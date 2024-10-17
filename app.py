from flask import Flask, render_template, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        query = request.form['query']
        try:
            result = subprocess.run(['aider', query], capture_output=True, text=True, check=True)
            return jsonify({'result': result.stdout})
        except subprocess.CalledProcessError as e:
            return jsonify({'error': str(e), 'output': e.output}), 400
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
