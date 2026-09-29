# ATC Priority Landing Simulator - Run Guide

**Last Updated:** 2026-09-29 20:15 IST

---

## Bug Fix Summary

### **Bug: Static GUI Tables**
The priority queue and holding pattern tables in the JavaFX GUI were not updating dynamically. They only displayed initial data and did not reflect changes after flights were registered, processed, or moved to holding patterns.

### **Root Cause**
- `FlightRegistry` lacked methods to expose the internal state of priority queue and holding pattern
- `DashboardController.updateTables()` only updated the registry table, not the priority/holding tables

### **Fix Applied**
1. Added `getPriorityQueueContents()` and `getHoldingPatternContents()` methods to `FlightRegistry`
2. Updated `DashboardController.updateTables()` to refresh all tables
3. Ensured `updateTables()` is called after every registry mutation

---

## Quick Start (Core System - No JavaFX Required)

### 1. Compile Core System
```powershell
# Navigate to project root
cd C:\Users\expos\Downloads\DSA_JAVA

# Compile all core classes (no GUI)
javac -d bin src/atc/model/*.java src/atc/datastructures/*.java src/atc/exceptions/*.java src/atc/util/*.java src/atc/persistence/*.java
```

### 2. Run Tests
```powershell
# Test model (Aircraft hierarchy, priority calculation)
java -cp bin atc.model.TestModel

# Test data structures
java -cp bin atc.datastructures.TestDataStructures

# Test flight registry integration
java -cp bin atc.model.TestFlightRegistry
```

**Expected Output:** All tests pass with ✅ messages

---

## JavaFX GUI (Optional - Requires JavaFX SDK)

### Prerequisites
- JavaFX SDK installed at: `C:\Users\expos\Downloads\javafx-sdk-21.0.12`
- JDK 21 in PATH: `C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin`

### 1. Set Environment Variables
```powershell
$env:PATH += ";C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin"
$env:JAVAFX_HOME = "C:\Users\expos\Downloads\javafx-sdk-21.0.12"
```

### 2. Compile GUI
```powershell
# Compile core first (if not already done)
javac -d bin src/atc/model/*.java src/atc/datastructures/*.java src/atc/exceptions/*.java src/atc/util/*.java src/atc/persistence/*.java

# Compile GUI with JavaFX modules
javac --module-path $env:JAVAFX_HOME\lib --add-modules javafx.controls,javafx.fxml -cp bin -d bin src/atc/gui/*.java
```

### 3. Launch GUI
```powershell
java --module-path $env:JAVAFX_HOME\lib --add-modules javafx.controls,javafx.fxml -cp bin atc.gui.ATCDemoApp
```

**Expected:** JavaFX window opens with ATC dashboard (flight registration form, priority queue table, holding pattern table)

---

## Project Structure

```
DSA_JAVA/
├── src/atc/
│   ├── model/           # Aircraft hierarchy, Registry, History
│   ├── datastructures/  # 5 custom DS implementations
│   ├── exceptions/      # 3 custom exceptions
│   ├── persistence/     # JSON file I/O
│   ├── gui/             # JavaFX controllers
│   └── util/            # FlightType enum
├── data/
│   └── sample_flights.json  # CCJ airport data
├── docs/
│   ├── PROJECT_REPORT.tex   # LaTeX report
│   ├── PROJECT_REPORT.html  # HTML report
│   ├── PROJECT_REPORT.txt   # Text report
│   ├── STATUS_LOG.md        # Project status
│   └── RUN_GUIDE.md         # This file
└── bin/                 # Compiled classes
```

---

## Sample Data

Current sample data: `data/sample_flights.json`

Contains 6 flights for **CCJ (Calicut International Airport)**:
- AI541, AI542 (Commercial CCJ↔DEL)
- IX445, IX446 (Commercial CCJ↔BOM)
- CG201 (Cargo CCJ→MAA)
- EM999 (Emergency, fuel 8%)

---

## Git Operations

### Check Status
```powershell
git status
git log --oneline -10
```

### Add New Commits
```powershell
git add <files>
git commit -m "feat: description"
git push origin main
```

### Repository
- **URL:** `https://github.com/fadimuhammed/Atc.git`
- **Branch:** `main`

---

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| `javac not recognized` | Add JDK bin to PATH: `$env:PATH += ";C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin"` |
| `module not found: javafx.controls` | Set `JAVAFX_HOME` and use `--module-path` |
| `package atc.model does not exist` | Compile core first, then GUI with `-cp bin` |
| `class not found` | Ensure `bin/` is in classpath |

---

## Feature List

### Core System ✅
- Aircraft hierarchy (Commercial/Cargo/Emergency)
- Priority scoring with configurable weights
- 5 custom data structures from scratch
- Hash table with chaining
- Binary search on sorted registry
- Merge sort implementation
- Custom exception handling
- JSON file persistence
- Landing history with statistics

### JavaFX GUI ✅
- Flight registration form
- Registry table view
- Priority queue table
- Holding pattern table
- Process landing button
- Generate report button

### Testing ✅
- Model tests: 7/7 passed
- Data structure tests: 6/6 passed
- Integration tests: 4/4 passed

---

## Deliverables Checklist

- [x] Complete Java source code (24 files)
- [x] Custom data structures (5 implemented)
- [x] OOP concepts demonstrated
- [x] Complexity analysis documented
- [x] Exception handling
- [x] File persistence (JSON)
- [x] Sample data included
- [x] Project report (LaTeX/HTML/TXT)
- [x] Git repository with commit history
- [x] JavaFX GUI framework
- [ ] UML class diagram (pending)
- [ ] Final PDF export (pending)

---

## Next Steps

1. **Create UML diagrams** using IntelliJ/Eclipse or PlantUML
2. **Generate project report PDF** - Print HTML to PDF or use Overleaf
3. **Viva preparation** - Document key algorithms, complexity, OOP design
4. **Test edge cases** - Full holding pattern, duplicate IDs, etc.

---

**Last Updated:** 2026-09-29 19:00 IST