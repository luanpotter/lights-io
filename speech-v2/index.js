const fs = require('fs');

const speech = require('@google-cloud/speech');
const record = require('node-record-lpcm16');

const AWS = require('aws-sdk');
const Stream = require('stream');
const Speaker = require('speaker');

AWS.config.loadFromPath('./aws-key.json');
const Polly = new AWS.Polly();
const SPEAKER_CONFIG = {
  channels: 1,
  bitDepth: 16,
  sampleRate: 16000
};

const speak = (text) => {
  let params = {
	  'Text': text ,
	  'OutputFormat': 'pcm',
	  'VoiceId': 'Kendra'
  };

  Polly.synthesizeSpeech(params, (err, data) => {
	  if (err) {
	  	console.log(err.code);
	  } else if (data) {
	  	if (data.AudioStream instanceof Buffer) {
        const buffer = new Stream.PassThrough();
        const player = new Speaker(SPEAKER_CONFIG);

        buffer.end(data.AudioStream);
        buffer.pipe(player);

        player.on('finish', () => {
            buffer.unpipe(player);
            buffer.end();
            player.close();
        });
	  	}
	  }
  });
};

function streamingMicRecognize(bot) {
  const projectId = 'lights-io';
  const client = new speech.SpeechClient({ projectId });
  const encoding = 'LINEAR16';
  const sampleRateHertz = 16000;
  const languageCode = 'en-US';

  const request = {
	config: {
	  encoding,
	  sampleRateHertz,
	  languageCode,
	},
	interimResults: false, // If you want interim results, set this to true
  };

  const recognizeStream = client
	.streamingRecognize(request)
	.on('error', console.error)
	.on('data', data => {
    if (data.results[0] && data.results[0].alternatives[0]) {
      const response = bot.process(data.results[0].alternatives[0].transcript);
      speak(response);
    }
  });

  // Start recording and send the microphone input to the Speech API
  record
	.start({
	  sampleRateHertz: sampleRateHertz,
	  threshold: 0,
	  // Other options, see https://www.npmjs.com/package/node-record-lpcm16#options
	  verbose: false,
	  recordProgram: 'arecord',
	  silence: '10.0',
	})
	.on('error', console.error)
	.pipe(recognizeStream);

  console.log('Listening, press Ctrl+C to stop.');
}

const Bot = require('./bot.js');
streamingMicRecognize(Bot.bot);
