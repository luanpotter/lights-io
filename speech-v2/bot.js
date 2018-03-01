class Bot {
  process(txt) {
    console.log('bot', txt);
    return txt;
  }
};

module.exports = { bot: new Bot() };
