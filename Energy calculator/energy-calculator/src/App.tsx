import React, { useState } from "react";

// Define types for the state
type CalculatorState = {
  electricityUsage: number;
  gasUsage: number;
  electricityRate: number;
  gasRate: number;
  totalBill: number | null;
};

function App() {
  // Initialize state with default values
  const [state, setState] = useState<CalculatorState>({
    electricityUsage: 0,
    gasUsage: 0,
    electricityRate: 0,
    gasRate: 0,
    totalBill: null,
  });

  // Function to update state for each input field
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof CalculatorState) => {
    setState({
      ...state,
      [field]: parseFloat(e.target.value) || 0, // Convert the input to a number
    });
  };

  // Function to calculate the bill
  const calculateBill = () => {
    const electricityCost = state.electricityUsage * state.electricityRate;
    const gasCost = state.gasUsage * state.gasRate;
    const finalBill = electricityCost + gasCost;
    setState((prevState) => ({ ...prevState, totalBill: finalBill }));
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-2xl font-bold mb-6 text-center">Energy Cost Calculator</h1>
        <form className="space-y-4">
          {/* Electricity Usage Input */}
          <div>
            <label htmlFor="electricityUsage" className="block text-sm font-medium text-gray-700">
              Electricity Usage (kWh)
            </label>
            <input
              type="number"
              id="electricityUsage"
              value={state.electricityUsage}
              onChange={(e) => handleChange(e, "electricityUsage")}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>

          {/* Gas Usage Input */}
          <div>
            <label htmlFor="gasUsage" className="block text-sm font-medium text-gray-700">
              Gas Usage (MJ)
            </label>
            <input
              type="number"
              id="gasUsage"
              value={state.gasUsage}
              onChange={(e) => handleChange(e, "gasUsage")}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>

          {/* Electricity Rate Input */}
          <div>
            <label htmlFor="electricityRate" className="block text-sm font-medium text-gray-700">
              Electricity Rate (c/kWh)
            </label>
            <input
              type="number"
              id="electricityRate"
              value={state.electricityRate}
              onChange={(e) => handleChange(e, "electricityRate")}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>

          {/* Gas Rate Input */}
          <div>
            <label htmlFor="gasRate" className="block text-sm font-medium text-gray-700">
              Gas Rate (c/MJ)
            </label>
            <input
              type="number"
              id="gasRate"
              value={state.gasRate}
              onChange={(e) => handleChange(e, "gasRate")}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>

          {/* Calculate Button */}
          <div className="flex justify-center mt-4">
            <button
              type="button"
              onClick={calculateBill}
              className="w-full px-4 py-2 bg-indigo-600 text-white font-semibold rounded-md hover:bg-indigo-700"
            >
              Calculate Bill
            </button>
          </div>
        </form>

        {/* Display Results */}
        {state.totalBill !== null && (
          <div className="mt-6 text-center">
            <h2 className="text-lg font-medium">Your Total Bill</h2>
            <p className="text-2xl font-bold">${state.totalBill.toFixed(2)}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
