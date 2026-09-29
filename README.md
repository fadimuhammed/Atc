# ATC Priority Landing Simulator

## Project Overview

Smart Air Traffic Control Priority Landing Simulator implemented in Java with custom data structures from scratch. Integrates Data Structures & Algorithms with Object-Oriented Programming concepts.

## Features

✅ **Core Implementation Complete**
- Aircraft hierarchy with inheritance (Commercial/Cargo/Emergency)
- Custom data structures: Priority Queue, Circular Queue, Hash Table, Sorted Array, Stack
- Merge Sort, Binary Search, Hash Lookup
- Priority scoring algorithm with configurable weights
- Custom exception handling
- JSON file persistence
- Landing history with statistics

## Project Structure

```
src/atc/
├── model/           Aircraft hierarchy, Registry, History, Calculator
├── datastructures/  Custom DS implementations (5 structures)
├── exceptions/      DuplicateFlightIDException, etc.
├── persistence/     File I/O with manual JSON parsing
├── gui/            JavaFX controllers (Demo)
└── util/           FlightType enum

data/
├── sample_flights.json  FlightRadar24 sample data
└── test_output.json     Runtime output
```

## Running the Project

### Core System (Console)
```bash
javac -d bin src/atc/**/*.java
java -cp bin atc.model.TestModel
java -cp bin atc.datastructures.TestDataStructures
java -cp bin atc.model.TestFlightRegistry
```

### JavaFX GUI
Requires JavaFX SDK:
```bash
javac --module-path /path/to/javafx/lib --add-modules javafx.controls -d bin src/atc/**/*.java
java --module-path /path/to/javafx/lib --add-modules javafx.controls -cp bin atc.gui.ATCDemoApp
```

## Data Structures Implemented

1. **Array-based Priority Queue** (Heap) - Landing order
2. **Circular Queue** - Holding pattern
3. **Hash Table** (chaining) - Flight ID lookup
4. **Sorted Array Registry** - Binary search range queries
5. **Stack** - Undo operations

## Algorithms

- **Merge Sort**: $O(n \log n)$ - Registry sorting
- **Binary Search**: $O(\log n)$ - Flight lookup
- **Hash Lookup**: $O(1)$ average - Flight ID

## Test Results

All tests passing:
- Model tests: Aircraft creation, priority calculation
- DS tests: Stack, Queue, Heap, Hash Table operations
- Integration tests: Registry, landing processing, file I/O

## Deliverables

- ✅ Complete Java source code (Plain Java, no external deps)
- ✅ UML class diagram (from code structure)
- ✅ Complexity analysis (in PROJECT_REPORT.tex)
- ✅ Sample data (data/sample_flights.json)
- ✅ Project report (LaTeX formatted)
- ✅ Test cases with 100% pass rate

## Requirements Met

**OOP Concepts:**
- Classes & Objects ✓
- Encapsulation ✓
- Inheritance ✓
- Polymorphism ✓
- Abstraction ✓
- Exception handling ✓
- File handling ✓
- Packages ✓

**DSA Concepts:**
- Array, Stack, Queue, Priority Queue ✓
- Linked List (hash chaining) ✓
- Hash Table ✓
- Sorting (Merge Sort) ✓
- Searching (Binary Search) ✓
- Complexity analysis ✓

## Report

LaTeX project report available at: `docs/PROJECT_REPORT.tex`

## Next Steps

- JavaFX GUI with real-time visualization
- Enhanced reporting dashboard
- Multi-airport simulation
- Graph-based conflict detection
