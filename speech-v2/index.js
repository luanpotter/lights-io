#!/bin/node

const fs = require('fs');

const speech = require('@google-cloud/speech');

const AWS = require('aws-sdk');
const Stream = require('stream');
const ncs = new (require('netcat/server'))();
const ncc = new (require('netcat/client'))();

AWS.config.loadFromPath('./aws-key.json');
const Polly = new AWS.Polly();

const encoding = 'LINEAR16';
const sampleRateHertz = 16000;

const speak = (text) => {
  let params = {
	  'Text': text ,
	  'OutputFormat': 'pcm',
    'SampleRate': '16000',
	  'VoiceId': 'Kendra'
  };

  Polly.synthesizeSpeech(params, (err, data) => {
	  if (err) {
	  	console.log(err.code);
	  } else if (data) {
	  	if (data.AudioStream instanceof Buffer) {
        console.log('speaking');
        const player = ncc.addr('192.168.100.4').port(7891).connect().stream();
        const buffer = new Stream.PassThrough();

        buffer.end(data.AudioStream);
        buffer.pipe(player);

        player.on('finish', () => {
            buffer.unpipe(player);
            buffer.end();
        });
	  	}
	  }
  });
};

function streamingMicRecognize(bot) {
  const projectId = 'lights-io';
  const client = new speech.SpeechClient({ projectId });
  const languageCode = 'en-US';

  const request = {
	  config: {
	    encoding,
	    sampleRateHertz,
	    languageCode,
	  },
	  interimResults: false, // If you want interim results, set this to true
  };

  let recognizeStream;
  const maker = () => client
	.streamingRecognize(request)
    .on('error', () => make())
	.on('data', data => {
    console.log('id\'ed');
    if (data.results[0] && data.results[0].alternatives[0]) {
      const response = bot.process(data.results[0].alternatives[0].transcript);
      console.log('response: ' + response);
      if (response) {
        speak(response);
      }
    }
  });

  const piper = ncs.port(7890).listen();
  const make = () => {
    if (recognizeStream) recognizeStream.end();
    recognizeStream = maker();
    piper.pipe(recognizeStream);
  };
  make();
  console.log('Listening, press Ctrl+C to stop.');
}

const Bot = require('./bot.js');
streamingMicRecognize(Bot.bot);
