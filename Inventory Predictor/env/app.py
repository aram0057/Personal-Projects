from flask import Flask, jsonify
from flask_restful import Api, Resource

app = Flask(__name__)
api = Api(app)

@app.route('/')
def home():
    return "Welcome to the Inventory Prediction API! Use /predict to upload your inventory data."

class InventoryPrediction(Resource):
    def post(self):
        # For now, just return a hello world message
        return jsonify({"message": "Hello, World!"})

api.add_resource(InventoryPrediction, '/test')

if __name__ == '__main__':
    app.run(debug=True)





