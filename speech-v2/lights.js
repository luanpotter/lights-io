const request = require('request-promise');

const url = process.argv[2];
const options = path => ({
  method: 'PUT',
  url: url + path,
});

module.exports = {
  lights: state => request(options('/toggle?s=' + (state ? 'on' : 'off'))),
};
