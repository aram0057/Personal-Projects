from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
import pandas as pd

# Example function to train a basic sales prediction model
def predict_sales(data):
    X = data[['units_sold', 'stock_level']]  # Features
    y = data['units_sold']  # Target (e.g., future sales)

    # Split data into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

    # Train a random forest regressor
    model = RandomForestRegressor()
    model.fit(X_train, y_train)

    # Return predictions
    predictions = model.predict(X_test)
    return predictions
