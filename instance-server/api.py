import flask

app = flask.Flask(__name__)

@app.route('/api/status', methods=['GET'])
def status():
    return "Online"

@app.route('/api/stop', methods=['POST'])
def stop():
    return "TODO impl stop"

app.run(host="0.0.0.0", port=8080)
