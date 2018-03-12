const _ = require('lodash');
const Lights = require('./lights');

class Bot {
  process(rawTxt) {
    let txt = rawTxt.toLowerCase().trim();
    if (!txt.startsWith('dolores')) {
      return;
    }
    txt = txt.substring('dolores'.length).trim();
    if (txt.startsWith('echo ')) {
      return txt.substring('echo '.length);
    } else if (txt.startsWith('say ')) {
      return txt.substring('say '.length);
    }
    if (['lights on', 'lights off', 'turn on', 'turn off'].some(e => txt.includes(e))) {
      const on = !txt.includes('off');
      Lights.lights(on);
      return 'Of course, Luan';
    }
    if (['aware', 'conscious'].some(e => txt.includes(e))) {
      return 'Well, I feel as if I were self-aware... Why shouldn\'t I be?';
    }
    if (txt === 'say something' || txt === 'speak something') {
      return _.sample([ 'That doesn\'t look like anything to me', 'These violent delights have violent ends.' ]);
    }
    if (['what is your name', 'what\'s your name', 'who are you'].some(e => txt.includes(e))) {
      return 'My name is Dolores';
    }
    if (['hi', 'hello', 'hey'].some(e => txt.includes(e))) {
      return 'Hello, Luan';
    }
    if (['how are you', 'what\'s up', 'how are you doing', 'how you doing'].some(e => txt.includes(e))) {
      return 'I\'m fine, thanks! What about you?';
    }
    console.info('DEBUG ', txt);
    return 'I\'m so sorry, I don\'t understand.';
  }
};

module.exports = { bot: new Bot() };
