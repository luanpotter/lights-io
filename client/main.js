const firebase = require('firebase');
const onoff = require('onoff');
const config = require('./config');

const Gpio = onoff.Gpio;
const led = new Gpio(14, 'out');
const consume = value => led.write(value ? 1 : 0);

console.log('Initializing...');
firebase.initializeApp(config);

console.log('Listening...');
firebase.database().ref('/lights/5629499534213120').on('value', snapshot => consume(snapshot.val()));
