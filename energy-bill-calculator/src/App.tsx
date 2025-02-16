import React, { useState, useEffect } from "react";

interface Result {
  newBill: number;
  monthlySavings: number;
  quarterlySavings: number;
  annualSavings: number;
}

// Helper: restrict key input to digits and a single dot.
const handleNumericKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
  const char = e.key;
  if (!/[0-9.]/.test(char)) {
    e.preventDefault();
  }
};

// Helper: convert empty string to 0, otherwise parse as float.
const toNumber = (value: string): number =>
  value.trim() === "" ? 0 : parseFloat(value);

export default function EnergyBillCalculator() {
  // Use resetTrigger to force re-mounting of child components when resetting.
  const [resetTrigger, setResetTrigger] = useState(0);
  const [notes, setNotes] = useState("");
  const [electricResult, setElectricResult] = useState<Result | null>(null);
  const [gasResult, setGasResult] = useState<Result | null>(null);

  const handleReset = () => {
    setResetTrigger((prev) => prev + 1);
    setNotes("");
    setElectricResult(null);
    setGasResult(null);
  };

  return (
    <div style={styles.container}>
      {/* Header with floating animation */}
      <style>
        {`
          @keyframes float {
            0% { transform: translateY(0px); }
            50% { transform: translateY(-10px); }
            100% { transform: translateY(0px); }
          }
        `}
      </style>
      <div style={styles.headerRow}>
        <div>
          <h1 style={styles.title}>Energy Bill Savings Calculator</h1>
          <h2 style={styles.subTitle}>Made by Abbi Kamak using React</h2>
        </div>
        <button style={styles.resetButton} onClick={handleReset}>
          🔄 Reset All
        </button>
      </div>

      <div style={styles.threeColumnContainer}>
        {/* Electricity Form Column */}
        <div style={styles.column}>
          <ElectricForm key={resetTrigger} onResultChange={(res) => setElectricResult(res)} />
          {electricResult && (
            <SavingsDisplay result={electricResult} label="⚡ Electricity Savings" />
          )}
        </div>

        {/* Gas Form Column */}
        <div style={styles.column}>
          <GasForm key={resetTrigger} onResultChange={(res) => setGasResult(res)} />
          {gasResult && <SavingsDisplay result={gasResult} label="🔥 Gas Savings" />}
        </div>

        {/* Notes Column */}
        <div style={{ ...styles.column, flex: "1" }}>
          <fieldset style={styles.fieldset}>
            <legend style={styles.legend}>📝 Notes</legend>
            <textarea
              style={{ ...styles.input, height: "200px", resize: "vertical" }}
              placeholder="Type your notes here..."
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
            ></textarea>
          </fieldset>
        </div>
      </div>

      <div style={styles.combinedSavingsContainer}>
        <h2 style={styles.sectionTitle}>💰 Combined Savings</h2>
        <CombinedSavings electric={electricResult} gas={gasResult} />
      </div>
    </div>
  );
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: "1200px",
    margin: "0 auto",
    padding: "20px",
    fontFamily: "Arial, sans-serif",
  },
  headerRow: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "20px",
  },
  title: {
    margin: 0,
    fontSize: "32px",
    animation: "float 3s ease-in-out infinite",
  },
  subTitle: {
    margin: 0,
    fontSize: "16px",
    color: "#555",
    animation: "float 3s ease-in-out infinite",
  },
  resetButton: {
    padding: "8px 12px",
    fontSize: "16px",
    cursor: "pointer",
  },
  threeColumnContainer: {
    display: "flex",
    gap: "20px",
    justifyContent: "space-between",
    flexWrap: "wrap",
  },
  column: {
    flex: "1",
    minWidth: "300px",
    maxWidth: "350px",
    border: "1px solid #ccc",
    padding: "15px",
    borderRadius: "5px",
  },
  combinedSavingsContainer: {
    marginTop: "30px",
    padding: "15px",
    border: "1px solid #aaa",
    borderRadius: "5px",
    textAlign: "center",
  },
  sectionTitle: {
    marginBottom: "15px",
  },
  fieldset: {
    border: "1px solid #ccc",
    borderRadius: "5px",
    padding: "15px",
    marginBottom: "20px",
  },
  legend: {
    fontWeight: "bold",
    padding: "0 5px",
  },
  formGroup: {
    marginBottom: "15px",
  },
  label: {
    display: "block",
    marginBottom: "5px",
    fontSize: "14px",
  },
  input: {
    width: "100%",
    padding: "8px",
    borderRadius: "3px",
    border: "1px solid #ccc",
    boxSizing: "border-box",
  },
  inlineGroup: {
    display: "flex",
    gap: "10px",
  },
  halfInput: {
    flex: "1",
  },
  error: {
    color: "red",
    fontSize: "14px",
    marginTop: "5px",
  },
};

function SavingsDisplay({ result, label }: { result: Result; label: string }) {
  const { newBill, monthlySavings, quarterlySavings, annualSavings } = result;
  const savingsColor = monthlySavings >= 0 ? "green" : "red";

  return (
    <div style={{ marginTop: "15px", borderTop: "1px solid #ddd", paddingTop: "15px" }}>
      <h3>{label}</h3>
      <p>New Bill: ${newBill.toFixed(2)}</p>
      <p style={{ color: savingsColor }}>Monthly Savings: ${monthlySavings.toFixed(2)}</p>
      <p style={{ color: savingsColor }}>Quarterly Savings: ${quarterlySavings.toFixed(2)}</p>
      <p style={{ color: savingsColor }}>Annual Savings: ${annualSavings.toFixed(2)}</p>
    </div>
  );
}

function CombinedSavings({ electric, gas }: { electric: Result | null; gas: Result | null }) {
  if (!electric && !gas) return null;
  const monthlySavings = (electric?.monthlySavings || 0) + (gas?.monthlySavings || 0);
  const quarterlySavings = monthlySavings * 3;
  const annualSavings = monthlySavings * 12;
  const savingsColor = monthlySavings >= 0 ? "green" : "red";

  return (
    <div>
      <p style={{ color: savingsColor, fontSize: "18px" }}>
        Combined Monthly Savings: ${monthlySavings.toFixed(2)}
      </p>
      <p style={{ color: savingsColor, fontSize: "18px" }}>
        Combined Quarterly Savings: ${quarterlySavings.toFixed(2)}
      </p>
      <p style={{ color: savingsColor, fontSize: "18px" }}>
        Combined Annual Savings: ${annualSavings.toFixed(2)}
      </p>
    </div>
  );
}

interface ElectricFormProps {
  onResultChange: (result: Result) => void;
}

function ElectricForm({ onResultChange }: ElectricFormProps) {
  // Tariff Options: single_rate, time_of_use, demand
  const [tariffOption, setTariffOption] = useState("single_rate");

  // Single Rate Fields
  const [singleRate, setSingleRate] = useState<string>("");
  const [singleUsage, setSingleUsage] = useState<string>("");

  // Time-of-Use Fields
  const [peakRate, setPeakRate] = useState<string>("");
  const [peakUsage, setPeakUsage] = useState<string>("");
  const [offPeakRate, setOffPeakRate] = useState<string>("");
  const [offPeakUsage, setOffPeakUsage] = useState<string>("");

  // Demand Fields
  const [demandRate, setDemandRate] = useState<string>("");
  const [demandUsage, setDemandUsage] = useState<string>("");
  const [peakDemand, setPeakDemand] = useState<string>("");
  const [demandCharge, setDemandCharge] = useState<string>("");

  // Controlled Load Fields
  const [hasControlledLoad, setHasControlledLoad] = useState<boolean>(false);
  const [controlledLoadRate, setControlledLoadRate] = useState<string>("");
  const [controlledLoadUsage, setControlledLoadUsage] = useState<string>("");

  // Common Fields
  const [supplyChargeRate, setSupplyChargeRate] = useState<string>("");
  const [supplyChargeDays, setSupplyChargeDays] = useState<string>("");
  const [hasSolar, setHasSolar] = useState<boolean>(false);
  const [solarExport, setSolarExport] = useState<string>("");
  const [solarCreditRate, setSolarCreditRate] = useState<string>("");
  const [currentBill, setCurrentBill] = useState<string>("");

  // Concession Fields
  const [hasConcession, setHasConcession] = useState<boolean>(false);
  const [concessionAmount, setConcessionAmount] = useState<string>("");

  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {
    const newErrors: string[] = [];
    // Validate common fields
    if (toNumber(supplyChargeRate) < 0) newErrors.push("Supply charge rate cannot be negative.");
    if (toNumber(supplyChargeDays) < 0) newErrors.push("Supply charge days cannot be negative.");
    if (toNumber(currentBill) < 0) newErrors.push("Current bill cannot be negative.");
    if (hasSolar) {
      if (toNumber(solarExport) < 0) newErrors.push("Solar export cannot be negative.");
      if (toNumber(solarCreditRate) < 0) newErrors.push("Solar credit rate cannot be negative.");
    }
    // Validate tariff-specific fields
    let baseCost = 0;
    if (tariffOption === "single_rate") {
      if (toNumber(singleRate) < 0) newErrors.push("Tariff rate cannot be negative.");
      if (toNumber(singleUsage) < 0) newErrors.push("Usage cannot be negative.");
      baseCost = toNumber(singleUsage) * (toNumber(singleRate) / 100);
    } else if (tariffOption === "time_of_use") {
      if (toNumber(peakRate) < 0) newErrors.push("Peak tariff rate cannot be negative.");
      if (toNumber(offPeakRate) < 0) newErrors.push("Off-peak tariff rate cannot be negative.");
      if (toNumber(peakUsage) < 0) newErrors.push("Peak usage cannot be negative.");
      if (toNumber(offPeakUsage) < 0) newErrors.push("Off-peak usage cannot be negative.");
      baseCost =
        toNumber(peakUsage) * (toNumber(peakRate) / 100) +
        toNumber(offPeakUsage) * (toNumber(offPeakRate) / 100);
    } else if (tariffOption === "demand") {
      if (toNumber(demandRate) < 0) newErrors.push("Tariff rate cannot be negative.");
      if (toNumber(demandUsage) < 0) newErrors.push("Usage cannot be negative.");
      if (toNumber(peakDemand) < 0) newErrors.push("Peak demand cannot be negative.");
      if (toNumber(demandCharge) < 0) newErrors.push("Demand charge cannot be negative.");
      baseCost =
        toNumber(demandUsage) * (toNumber(demandRate) / 100) +
        toNumber(peakDemand) * toNumber(demandCharge);
    }

    // Add Controlled Load cost if enabled
    if (hasControlledLoad) {
      if (toNumber(controlledLoadRate) < 0) newErrors.push("Controlled load rate cannot be negative.");
      if (toNumber(controlledLoadUsage) < 0) newErrors.push("Controlled load usage cannot be negative.");
      baseCost += toNumber(controlledLoadUsage) * (toNumber(controlledLoadRate) / 100);
    }

    if (newErrors.length === 0) {
      baseCost += toNumber(supplyChargeRate) * toNumber(supplyChargeDays);
      const solarCredit = hasSolar ? toNumber(solarExport) * (toNumber(solarCreditRate) / 100) : 0;
      const newBill = baseCost - solarCredit;
      const effectiveCurrentBill = toNumber(currentBill) - (hasConcession ? toNumber(concessionAmount) : 0);
      const monthlySavings = effectiveCurrentBill - newBill;
      const quarterlySavings = monthlySavings * 3;
      const annualSavings = monthlySavings * 12;
      onResultChange({ newBill, monthlySavings, quarterlySavings, annualSavings });
    }
    setErrors(newErrors);
  }, [
    tariffOption,
    singleRate,
    singleUsage,
    peakRate,
    peakUsage,
    offPeakRate,
    offPeakUsage,
    demandRate,
    demandUsage,
    peakDemand,
    demandCharge,
    hasControlledLoad,
    controlledLoadRate,
    controlledLoadUsage,
    supplyChargeRate,
    supplyChargeDays,
    hasSolar,
    solarExport,
    solarCreditRate,
    currentBill,
    hasConcession,
    concessionAmount,
    onResultChange,
  ]);

  return (
    <fieldset style={styles.fieldset}>
      <legend style={styles.legend}>⚡ Electricity Bill Details</legend>
      <div style={styles.formGroup}>
        <label style={styles.label}>Tariff Option:</label>
        <select
          style={styles.input}
          value={tariffOption}
          onChange={(e) => setTariffOption(e.target.value)}
        >
          <option value="single_rate">Single Rate</option>
          <option value="time_of_use">Time of Use</option>
          <option value="demand">Demand</option>
        </select>
      </div>

      {tariffOption === "single_rate" && (
        <>
          <div style={styles.formGroup}>
            <label style={styles.label}>Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={singleRate}
              onChange={(e) => setSingleRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={singleUsage}
              onChange={(e) => setSingleUsage(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </>
      )}

      {tariffOption === "time_of_use" && (
        <>
          <div style={styles.formGroup}>
            <label style={styles.label}>Peak Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={peakRate}
              onChange={(e) => setPeakRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Peak Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={peakUsage}
              onChange={(e) => setPeakUsage(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Off-Peak Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={offPeakRate}
              onChange={(e) => setOffPeakRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Off-Peak Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={offPeakUsage}
              onChange={(e) => setOffPeakUsage(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </>
      )}

      {tariffOption === "demand" && (
        <>
          <div style={styles.formGroup}>
            <label style={styles.label}>Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={demandRate}
              onChange={(e) => setDemandRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={demandUsage}
              onChange={(e) => setDemandUsage(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Peak Demand:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kW"
              value={peakDemand}
              onChange={(e) => setPeakDemand(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Demand Charge:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="$/kW"
              value={demandCharge}
              onChange={(e) => setDemandCharge(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </>
      )}

      {/* Controlled Load */}
      <div style={styles.formGroup}>
        <label style={styles.label}>🏊 Controlled Load:</label>
        <label style={{ marginRight: "15px" }}>
          <input
            type="radio"
            name="controlledLoad"
            value="yes"
            checked={hasControlledLoad}
            onChange={() => setHasControlledLoad(true)}
          />{" "}
          Yes
        </label>
        <label>
          <input
            type="radio"
            name="controlledLoad"
            value="no"
            checked={!hasControlledLoad}
            onChange={() => setHasControlledLoad(false)}
          />{" "}
          No
        </label>
      </div>
      {hasControlledLoad && (
        <>
          <div style={styles.formGroup}>
            <label style={styles.label}>Controlled Load Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={controlledLoadRate}
              onChange={(e) => setControlledLoadRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Controlled Load Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={controlledLoadUsage}
              onChange={(e) => setControlledLoadUsage(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </>
      )}

      {/* Common fields for Electricity */}
      <div style={styles.inlineGroup}>
        <div style={styles.halfInput}>
          <label style={styles.label}>Supply Charge Rate:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="$/day"
            value={supplyChargeRate}
            onChange={(e) => setSupplyChargeRate(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
        <div style={styles.halfInput}>
          <label style={styles.label}>Supply Charge Days:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="days"
            value={supplyChargeDays}
            onChange={(e) => setSupplyChargeDays(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
      </div>

      <div style={styles.formGroup}>
        <label style={styles.label}>☀️ Solar:</label>
        <label style={{ marginRight: "15px" }}>
          <input
            type="radio"
            name="hasSolar"
            value="yes"
            checked={hasSolar}
            onChange={() => setHasSolar(true)}
          />{" "}
          Yes
        </label>
        <label>
          <input
            type="radio"
            name="hasSolar"
            value="no"
            checked={!hasSolar}
            onChange={() => setHasSolar(false)}
          />{" "}
          No
        </label>
      </div>
      {hasSolar && (
        <>
          <div style={styles.formGroup}>
            <label style={styles.label}>Solar Export:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="kWh"
              value={solarExport}
              onChange={(e) => setSolarExport(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.formGroup}>
            <label style={styles.label}>Solar Credit Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/kWh"
              value={solarCreditRate}
              onChange={(e) => setSolarCreditRate(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </>
      )}

      {/* Concession Question */}
      <div style={styles.formGroup}>
        <label style={styles.label}>🤑 Concession:</label>
        <label style={{ marginRight: "15px" }}>
          <input
            type="radio"
            name="concession"
            value="yes"
            checked={hasConcession}
            onChange={() => setHasConcession(true)}
          />{" "}
          Yes
        </label>
        <label>
          <input
            type="radio"
            name="concession"
            value="no"
            checked={!hasConcession}
            onChange={() => setHasConcession(false)}
          />{" "}
          No
        </label>
      </div>
      {hasConcession && (
        <div style={styles.formGroup}>
          <label style={styles.label}>Concession Amount:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="$"
            value={concessionAmount}
            onChange={(e) => setConcessionAmount(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
      )}
      <div style={styles.formGroup}>
        <label style={styles.label}>Current Electric Bill:</label>
        <input
          style={styles.input}
          type="number"
          placeholder="$"
          value={currentBill}
          onChange={(e) => setCurrentBill(e.target.value)}
          onKeyPress={handleNumericKeyPress}
        />
      </div>

      

      {errors.length > 0 && (
        <div style={styles.error}>
          {errors.map((err, idx) => (
            <div key={idx}>{err}</div>
          ))}
        </div>
      )}
    </fieldset>
  );
}

interface GasFormProps {
  onResultChange: (result: Result) => void;
}

function GasForm({ onResultChange }: GasFormProps) {
  // Three steps for gas usage/tariff
  const [gasRate1, setGasRate1] = useState<string>("");
  const [gasUsage1, setGasUsage1] = useState<string>("");
  const [gasRate2, setGasRate2] = useState<string>("");
  const [gasUsage2, setGasUsage2] = useState<string>("");
  const [gasRate3, setGasRate3] = useState<string>("");
  const [gasUsage3, setGasUsage3] = useState<string>("");

  const [supplyChargeRate, setSupplyChargeRate] = useState<string>("");
  const [supplyChargeDays, setSupplyChargeDays] = useState<string>("");
  const [currentBill, setCurrentBill] = useState<string>("");

  // Concession for gas
  const [hasConcession, setHasConcession] = useState<boolean>(false);
  const [concessionAmount, setConcessionAmount] = useState<string>("");

  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {
    const newErrors: string[] = [];
    if (toNumber(gasRate1) < 0) newErrors.push("Step 1 tariff rate cannot be negative.");
    if (toNumber(gasUsage1) < 0) newErrors.push("Step 1 usage cannot be negative.");
    if (toNumber(gasRate2) < 0) newErrors.push("Step 2 tariff rate cannot be negative.");
    if (toNumber(gasUsage2) < 0) newErrors.push("Step 2 usage cannot be negative.");
    if (toNumber(gasRate3) < 0) newErrors.push("Step 3 tariff rate cannot be negative.");
    if (toNumber(gasUsage3) < 0) newErrors.push("Step 3 usage cannot be negative.");
    if (toNumber(supplyChargeRate) < 0) newErrors.push("Supply charge rate cannot be negative.");
    if (toNumber(supplyChargeDays) < 0) newErrors.push("Supply charge days cannot be negative.");
    if (toNumber(currentBill) < 0) newErrors.push("Current bill cannot be negative.");
    setErrors(newErrors);

    if (newErrors.length === 0) {
      const cost1 = toNumber(gasUsage1) * (toNumber(gasRate1) / 100);
      const cost2 = toNumber(gasUsage2) * (toNumber(gasRate2) / 100);
      const cost3 = toNumber(gasUsage3) * (toNumber(gasRate3) / 100);
      let baseCost = cost1 + cost2 + cost3;
      baseCost += toNumber(supplyChargeRate) * toNumber(supplyChargeDays);
      const newBill = baseCost;
      const effectiveCurrentBill = toNumber(currentBill) - (hasConcession ? toNumber(concessionAmount) : 0);
      const monthlySavings = effectiveCurrentBill - newBill;
      const quarterlySavings = monthlySavings * 3;
      const annualSavings = monthlySavings * 12;
      onResultChange({ newBill, monthlySavings, quarterlySavings, annualSavings });
    }
  }, [
    gasRate1,
    gasUsage1,
    gasRate2,
    gasUsage2,
    gasRate3,
    gasUsage3,
    supplyChargeRate,
    supplyChargeDays,
    currentBill,
    hasConcession,
    concessionAmount,
    onResultChange,
  ]);

  return (
    <fieldset style={styles.fieldset}>
      <legend style={styles.legend}>🔥 Gas Bill Details</legend>
      <div style={styles.formGroup}>
        <h4>Step 1</h4>
        <div style={styles.inlineGroup}>
          <div style={styles.halfInput}>
            <label style={styles.label}>Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/MJ"
              value={gasRate1}
              onChange={(e) => setGasRate1(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.halfInput}>
            <label style={styles.label}>Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="MJ"
              value={gasUsage1}
              onChange={(e) => setGasUsage1(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </div>
      </div>
      <div style={styles.formGroup}>
        <h4>Step 2</h4>
        <div style={styles.inlineGroup}>
          <div style={styles.halfInput}>
            <label style={styles.label}>Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/MJ"
              value={gasRate2}
              onChange={(e) => setGasRate2(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.halfInput}>
            <label style={styles.label}>Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="MJ"
              value={gasUsage2}
              onChange={(e) => setGasUsage2(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </div>
      </div>
      <div style={styles.formGroup}>
        <h4>Step 3</h4>
        <div style={styles.inlineGroup}>
          <div style={styles.halfInput}>
            <label style={styles.label}>Tariff Rate:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="c/MJ"
              value={gasRate3}
              onChange={(e) => setGasRate3(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
          <div style={styles.halfInput}>
            <label style={styles.label}>Usage:</label>
            <input
              style={styles.input}
              type="number"
              placeholder="MJ"
              value={gasUsage3}
              onChange={(e) => setGasUsage3(e.target.value)}
              onKeyPress={handleNumericKeyPress}
            />
          </div>
        </div>
      </div>

      <div style={styles.inlineGroup}>
        <div style={styles.halfInput}>
          <label style={styles.label}>Supply Charge Rate:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="$/day"
            value={supplyChargeRate}
            onChange={(e) => setSupplyChargeRate(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
        <div style={styles.halfInput}>
          <label style={styles.label}>Supply Charge Days:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="days"
            value={supplyChargeDays}
            onChange={(e) => setSupplyChargeDays(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
      </div>
      {/* Concession Question for Gas */}
      <div style={styles.formGroup}>
        <label style={styles.label}>🤑 Concession:</label>
        <label style={{ marginRight: "15px" }}>
          <input
            type="radio"
            name="concessionGas"
            value="yes"
            checked={hasConcession}
            onChange={() => setHasConcession(true)}
          />{" "}
          Yes
        </label>
        <label>
          <input
            type="radio"
            name="concessionGas"
            value="no"
            checked={!hasConcession}
            onChange={() => setHasConcession(false)}
          />{" "}
          No
        </label>
      </div>
      {hasConcession && (
        <div style={styles.formGroup}>
          <label style={styles.label}>Concession Amount:</label>
          <input
            style={styles.input}
            type="number"
            placeholder="$"
            value={concessionAmount}
            onChange={(e) => setConcessionAmount(e.target.value)}
            onKeyPress={handleNumericKeyPress}
          />
        </div>
      )}

      <div style={styles.formGroup}>
        <label style={styles.label}>Current Gas Bill:</label>
        <input
          style={styles.input}
          type="number"
          placeholder="$"
          value={currentBill}
          onChange={(e) => setCurrentBill(e.target.value)}
          onKeyPress={handleNumericKeyPress}
        />
      </div>

      

      {errors.length > 0 && (
        <div style={styles.error}>
          {errors.map((err, idx) => (
            <div key={idx}>{err}</div>
          ))}
        </div>
      )}
    </fieldset>
  );
}

export { EnergyBillCalculator };
