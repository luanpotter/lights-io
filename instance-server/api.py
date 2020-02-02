import flask
import subprocess

home="/home/enigmatica2-expert-server/enigmatica"
app = flask.Flask(__name__)

@app.route('/api/status', methods=['GET'])
def status():
    return "Online"

@app.route('/api/stop', methods=['POST'])
def stop():
    subprocess.run(home + "/stop.sh")
    while True:
        print("Waiting for the server to quit...")
        result = subprocess.run(home + "/is_running.sh").returncode
        if (result == 1):
            print("Server finished")
            break
        time.sleep(10)
    subprocess.run(home + "/quit.sh", stdin=None, stdout=None, stderr=None)
    return "Stopped"

app.run(host="0.0.0.0", port=8080)
