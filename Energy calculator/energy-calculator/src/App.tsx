import React, { useState } from 'react';

type CalculatorState = {
  electricityUsage: number;
  gasUsage: number;
  electricityRate: number;
  gasRate: number;
  totalBill: number | null;
  meterType: 'cl1' | 'cl2' | 'demand' | 'tou';
  solar: boolean;
  currentBill: number;
  shoulderRate?: number;
  peakRate?: number;
  offPeakRate?: number;
};

function App() {
  // State initialization with default values
  const [state, setState] = useState<CalculatorState>({
    electricityUsage: 0,
    gasUsage: 0,
    electricityRate: 0,
    gasRate: 0,
    totalBill: null,
    meterType: 'cl1',  // Default to CL1
    solar: false,
    currentBill: 0,
  });

  // Handle change for any input
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: keyof CalculatorState
  ) => {
    setState({
      ...state,
      [field]: field === 'solar' ? e.target.checked : parseFloat(e.target.value) || 0,
    });
  };

  // Handle change for selecting meter type
  const handleMeterTypeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setState({
      ...state,
      meterType: e.target.value as 'cl1' | 'cl2' | 'demand' | 'tou',
    });
  };

  // Function to calculate the electricity and gas bill
  const calculateBill = () => {
    let electricityCost = state.electricityUsage * state.electricityRate;
    let gasCost = state.gasUsage * state.gasRate;

    // Adjust for solar if user has it
    if (state.solar) {
      electricityCost = electricityCost * 0.7; // Example: 30% reduction for solar
    }

    // Add different rates for TOU
    if (state.meterType === 'tou') {
      const peakCost = state.electricityUsage * (state.peakRate || state.electricityRate);
      const offPeakCost = state.electricityUsage * (state.offPeakRate || state.electricityRate);
      electricityCost = peakCost + offPeakCost; // You can adjust how you handle peak/off-peak
    }

    const finalBill = electricityCost + gasCost;
    setState((prevState) => ({ ...prevState, totalBill: finalBill }));
  };

  return (
    <div className="min-h-screen flex flex-col md:flex-row bg-gray-50">
      {/* Electricity Form Section */}
      <div className="flex-1 p-8">
        <h1 className="text-2xl font-bold mb-6 text-center">Energy Cost Calculator</h1>

        <form className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Electricity Meter Type</label>
            <div className="space-x-4">
              <label className="inline-flex items-center">
                <input
                  type="radio"
                  value="cl1"
                  checked={state.meterType === 'cl1'}
                  onChange={handleMeterTypeChange}
                  className="form-radio"
                />
                <span className="ml-2">CL1</span>
              </label>
              <label className="inline-flex items-center">
                <input
                  type="radio"
                  value="cl2"
                  checked={state.meterType === 'cl2'}
                  onChange={handleMeterTypeChange}
                  className="form-radio"
                />
                <span className="ml-2">CL2</span>
              </label>
              <label className="inline-flex items-center">
                <input
                  type="radio"
                  value="demand"
                  checked={state.meterType === 'demand'}
                  onChange={handleMeterTypeChange}
                  className="form-radio"
                />
                <span className="ml-2">Demand</span>
              </label>
              <label className="inline-flex items-center">
                <input
                  type="radio"
                  value="tou"
                  checked={state.meterType === 'tou'}
                  onChange={handleMeterTypeChange}
                  className="form-radio"
                />
                <span className="ml-2">TOU</span>
              </label>
            </div>
          </div>

          {/* Conditional Input Fields Based on Meter Type */}
          {state.meterType === 'tou' && (
            <div>
              <label className="block text-sm font-medium text-gray-700">Peak Rate (c/kWh)</label>
              <input
                type="number"
                value={state.peakRate || ''}
                onChange={(e) => handleChange(e, 'peakRate')}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
              />
              <label className="block text-sm font-medium text-gray-700 mt-4">Off-Peak Rate (c/kWh)</label>
              <input
                type="number"
                value={state.offPeakRate || ''}
                onChange={(e) => handleChange(e, 'offPeakRate')}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
              />
            </div>
          )}

          {/* Electricity Usage */}
          <div>
            <label className="block text-sm font-medium text-gray-700">Electricity Usage (kWh)</label>
            <input
              type="number"
              value={state.electricityUsage}
              onChange={(e) => handleChange(e, 'electricityUsage')}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Solar Toggle */}
          <div className="mt-4">
            <label className="inline-flex items-center">
              <input
                type="checkbox"
                checked={state.solar}
                onChange={(e) => handleChange(e, 'solar')}
                className="form-checkbox"
              />
              <span className="ml-2">Do you have solar?</span>
            </label>
          </div>

          {/* Calculate Button */}
          <div className="flex justify-center mt-6">
            <button
              type="button"
              onClick={calculateBill}
              className="w-full px-4 py-2 bg-indigo-600 text-white font-semibold rounded-md hover:bg-indigo-700"
            >
              Calculate Bill
            </button>
          </div>
        </form>

        {/* Total Bill */}
        {state.totalBill !== null && (
          <div className="mt-6 text-center">
            <h2 className="text-lg font-medium">Your Total Bill</h2>
            <p className="text-2xl font-bold">${state.totalBill.toFixed(2)}</p>
          </div>
        )}
      </div>

      {/* Gas Form Section (Right Side) */}
      <div className="flex-1 p-8 bg-gray-200">
        <h2 className="text-2xl font-bold mb-6 text-center">Gas Usage</h2>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Gas Usage (MJ)</label>
          <input
            type="number"
            value={state.gasUsage}
            onChange={(e) => handleChange(e, 'gasUsage')}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Gas Rate (c/MJ)</label>
          <input
            type="number"
            value={state.gasRate}
            onChange={(e) => handleChange(e, 'gasRate')}
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md"
          />
        </div>

        <div className="mt-6">
          <h3 className="text-xl font-medium">Current Gas Bill</h3>
          <p className="text-2xl font-bold">${(state.gasUsage * state.gasRate / 100).toFixed(2)}</p>
        </div>
      </div>
    </div>
  );
}

export default App;
