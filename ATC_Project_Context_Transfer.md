# Context Transfer: Combined DSA + OOP Project (ATC Simulator)

Purpose: paste this into a new chat so the next assistant has full context. Everything below is taken from the uploaded documents or from decisions made in this conversation. Items I could not verify are marked **[UNCONFIRMED]**.

---

## 1. User preferences / working rules (apply to the next assistant)

- Accuracy over helpfulness. If something is unknown, say "I do not know". Never guess or fabricate.
- Distinguish verified facts, reasonable inferences, and opinions. Cite sources when claims depend on external information. Do not claim to have searched or verified something unless it was actually done.
- Ask for clarification when a question is ambiguous. Prefer concise and accurate over confident and speculative.
- The user is doing the coding themselves. The assistant should help by reviewing, debugging, explaining and sanity-checking, not by writing the full solution.

---

## 2. Who / what

- Institution: Amal Jyothi College of Engineering (Autonomous), Kanjirappally.
- Programme/batch (from the question paper header): B.Tech Computer Science and Engineering (Cyber Security), BTCY 2025-29, Semester 3 (S3).
- Two courses are assessed together through one project:
  - **DSA:** Data Structures and Algorithms
  - **OOP:** Object Oriented Programming in Java
- **Course-code discrepancy to be aware of:** the syllabus PDF shows **24CST201** (DSA) and **24CST203** (OOP-Java); the question paper shows **24CCT201** and **24CCT203**. I don't know why they differ (possibly a different branch code). Use the codes on whatever the user is submitting.

---

## 3. The question paper, stated explicitly

**Header:** Amal Jyothi College of Engineering Autonomous, Kanjirappally. B.Tech CSE (Cyber Security). BTCY2025-29-S3: 24CCT201 Data Structures and Algorithms, Project Evaluation. QP Code: 24CCT201/2024/P/2. Max Marks: 15 (DSA share). Time field is blank. Q.1(a), 15 marks, CO1-CO5, BL L3, PI 1.3.1.

**Title of assessment:** Combined Assessment 2025-26 (24CCT201 Data Structures and 24CCT203 Object Oriented Programming in Java), BTCY 2025-29 S3. Semester III Integrated Project-Based Assignment, (30 Marks) total.

### Combined project guidelines
1. Each group must consist of **3 students**.
2. Each student must be involved in **both OOP design and DS implementation**.
3. The application should have:
   - a) A clear class hierarchy using OOP concepts, handling all possible exceptions in a meaningful way.
   - b) **At least two non-trivial data structures implemented from scratch without using the Collections framework (ArrayList, Queue, Set).**
   - c) Algorithms such as sorting, searching, recursion, or traversal.
   - d) A simple **GUI using Swing/JavaFX**.

### Question
"Design and develop a Smart Library Management System (**Choose your own topic and update in the sheet provided below**) in Java using Object-Oriented Programming concepts and Data Structures & Algorithms."

- Topic updation link (Google Sheet): https://docs.google.com/spreadsheets/d/1QmzzmnEcBVK1qsFUN0mrpqPC5ohkPfV8h6r0ISUwisM/edit?usp=sharing (as extracted from the PDF text; the user should open it from the original PDF to be safe).

Your solution shall:
- Model the system using appropriate classes and OOP principles.
- Implement suitable data structures to manage books, members, and borrowing records (i.e. the sample domain; user's own topic replaces this).
- Incorporate efficient searching and sorting techniques.
- **Justify the choice of each data structure and analyze the time and space complexity** of the data structure choices and the implementations.
- Demonstrate proper exception handling and a user-friendly menu-driven interface.

### Course-wise evaluation (15 + 15)
**24CCT203 OOP Java (15 marks)** - students shall demonstrate: classes and objects, encapsulation, inheritance, polymorphism, interfaces/abstract classes, packages, exception handling, file handling, simple GUI using Swing/JavaFX, modular program design.

**24CCT201 DSA (15 marks)** - students shall justify and implement suitable data structures for different modules. The project must include implementation and application of **at least four of the following:**
- Array
- Linked List
- Stack
- Queue
- Binary Search Tree
- Hash Table
- Graph *(optional extension)*
- Heap/Priority Queue *(optional extension)*

Students shall also implement and analyze: searching algorithms, sorting algorithms, time complexity analysis (best, average, worst), space complexity analysis. A **short report** shall justify the selection of each data structure for its corresponding operation.

### Deliverables
1. Complete Java source code
2. UML class diagram
3. Algorithm/pseudocode
4. Complexity analysis
5. Output screenshots
6. Presentation and viva demonstration
7. Project report (5-10 pages)

### Rubrics
**24CCT203 OOP (15):**
| Criterion | Marks |
|---|---|
| Object-Oriented Design (classes, objects, encapsulation) | 3 |
| Inheritance, Polymorphism & Abstraction | 3 |
| Exception Handling, GUI Implementation | 3 |
| Code Quality, Modularity & Documentation | 3 |
| Demonstration and Viva | 3 |

**24CCT201 DSA (15):**
| Criterion | Marks |
|---|---|
| Appropriate Selection and Implementation of Data Structures | 5 |
| Searching and Sorting Algorithms | 3 |
| Complexity Analysis and Justification | 3 |
| Algorithm Design and Correctness | 2 |
| Demonstration and Viva | 2 |

### Sample project in the QP (Smart Library Management System)
Problem statement: menu-driven system for a college library applying OOP and appropriate DS&A; efficient management of books and users with optimized searching, sorting and borrowing.
Functional requirements: (1) add/update/delete book records; (2) register and manage members; (3) issue and return books; (4) search books by Book ID, Title, Author; (5) display available and issued books; (6) sort books by Title, Author, Publication Year; (7) maintain borrowing history; (8) reports: most borrowed books, available books, issued books; (9) proper exception handling and **file persistence**.

---

## 4. Syllabus facts used (from the first uploaded PDF)

- **DSA (24CST201):** L-T-P-R 3-1-0-0, 4 credits, PCC, introduced 2024. Modules: M1 Basic Concepts (8h: complexity, asymptotic notation); M2 Arrays and Searching (10h: polynomial via arrays, sparse matrix, stacks, infix-postfix, queues, circular queues, priority queues, deques, linear/binary search); M3 Linked List and Memory Management (12h); M4 Trees and Graphs (8h); M5 Sorting and Hashing (10h: selection, insertion, quick, merge, heap sort, hashing); Module i (6h, non-instructional).
- **OOP-Java (24CST203):** 3-1-0-0, 4 credits. M1 Introduction (8h); M2 Core Java Fundamentals (10h: classes, constructors, overloading, access control, static/final, Scanner, inheritance, overriding, dynamic dispatch, abstract classes, Object class); M3 More features (10h: packages, interfaces, exceptions, multithreading); M4 Java Libraries (7h: strings, I/O, serialization); M5 GUI (10h: event handling, Swing, JDBC basics); Module i (8h: JavaFX, Spring Boot intro, JDBC).

---

## 5. Conversation history and decisions

1. **Initial task (user-supplied prompt template):** act as a professor/curriculum evaluator and design 5 integrated project proposals combining both courses. The template's placeholders (student level, implementation constraint, timeline) were **never filled in by the user**. I assumed 2nd-year undergraduates, "core structures written from scratch", and a 4-6 week timeline. **[UNCONFIRMED]** apart from what the QP states (from-scratch, no Collections; S3 batch).
2. **Scope:** user first restricted scope to Modules 1-2 of both syllabi. The five proposals were:
   1. Air Traffic Control Priority Landing Simulator
   2. Algebraic Expression & Polynomial Engine
   3. Multi-Channel Customer Service Queuing System
   4. Sparse Terrain / Game-Grid Compression Engine
   5. Browser History & Priority Bookmark Navigator
3. **Decision:** I recommended Project 1 (ATC). The user chose it and said not to expand scope ("no need to proceed").
4. **Decision:** the user will code it themselves. I agreed to help with review, debugging, concept explanation and edge cases, not full solutions.
5. **Gap check against the actual QP** found the original ATC design was missing: a 4th data structure, a sorting algorithm, GUI, exception handling, packages, file handling, formal complexity analysis.
6. **Decision:** scope expanded beyond Modules 1-2 to satisfy the QP (hash table, sorting, GUI, packages, exceptions, file I/O are all outside Modules 1-2).

---

## 6. Current agreed project design

**Title:** Smart Air Traffic Control (ATC) Priority Landing Simulator using Java and Data Structures

**Problem statement:** Menu-driven ATC landing simulator for a small airport applying OOP and appropriate DS&A, enabling priority-based scheduling of landings with optimized searching, sorting and queue management under limited runway and airspace capacity.

**Abstract (submission-ready):** Runway-scheduling engine managing landing requests from commercial, cargo and emergency aircraft. Custom array-based priority queue decides landing order from a dynamically computed priority score (fuel level, emergency status, wait time); a circular queue models the holding pattern under fixed capacity; a hash table gives O(1) average flight-ID lookup; binary search over a sorted registry supports time/priority-range queries. Registry views use a from-scratch sorting algorithm (merge/quick sort) by ETA, fuel, or priority. OOP design uses an abstract `Aircraft` class with `CommercialFlight`, `CargoFlight`, `EmergencyFlight` (inheritance, dynamic dispatch), custom exceptions, file-based persistence of flight logs, and a Swing GUI for live queue monitoring and flight registration.

**Functional requirements:**
1. Register, update, cancel flights.
2. Assign/manage priority (fuel, emergency flag, arrival order).
3. Holding pattern via circular queue when runway unavailable.
4. Process landings in priority order (custom priority queue).
5. Search by Flight ID (hash table) and by scheduled time/priority range (binary search on sorted registry).
6. Sort/display registry by ETA, fuel level, priority score.
7. Display holding-pattern queue and next-to-land aircraft.
8. Landing history log persisted to file.
9. Reports: emergency landings processed, average wait time by aircraft type, runway utilization.
10. Exception handling (duplicate flight ID, holding pattern full, invalid priority) and a Swing GUI.

**Data structures:** array-based priority queue, circular queue, hash table, sorted array + binary search.
**Algorithms:** merge or quick sort (from scratch, recursion), binary search, hash lookup; write-up comparing hash O(1) average vs binary search O(log n).
**OOP:** abstract `Aircraft` + subclasses, `getPriorityScore()` overridden per type (dynamic dispatch feeds the priority queue), interface such as `Schedulable`, `super()` constructor chaining, `static` counter, `final` aircraft ID, method overloading (`requestLanding(...)`), custom exceptions (`DuplicateFlightIDException`, `HoldingPatternFullException`, `InvalidPriorityException`), packages `atc.model`, `atc.datastructures`, `atc.gui`, `atc.exceptions`.
**Key technical challenge:** keeping the priority queue and holding-pattern queue consistent as fuel depletes and priorities change (re-sort vs re-insert).
**Suggested build order:** Aircraft hierarchy, then priority queue in isolation, then circular queue, then hash table and binary search, then sorting, then exceptions and file I/O, then GUI.

---

## 7. Corrections and risks the next assistant should know

1. **Possible rubric risk on "at least four" data structures.** The QP lists Graph and Heap/Priority Queue as *optional extensions*. My design's four structures are Array (sorted registry), Queue (circular), Hash Table, and Priority Queue. If the evaluator does not count the priority queue toward the four, only three count. I do not know how the evaluator will count this. **Safer option: add a fourth core structure** (e.g. a Stack for a cancellation/undo history, or a BST or Linked List). This is not yet decided.
2. **Correction of an earlier statement:** I said "3 of 15 OOP marks depend on the GUI." The rubric row is combined: "Exception Handling, GUI Implementation" = 3 marks together. The GUI is still required by the guidelines.
3. **Tension in the QP:** guideline 3(b) says at least *two* non-trivial from-scratch structures; the DSA section says at least *four* of the listed ones. Design for four, implemented from scratch with no `ArrayList`, `Queue`, or `Set`. Whether the `HashMap` is allowed is not stated explicitly in the QP text I saw (only ArrayList, Queue, Set are named), so implement the hash table from scratch to be safe.
4. QP says "Choose your own topic and update in the sheet": the user still needs to enter the ATC topic in that Google Sheet. **[NOT CONFIRMED as done]**
5. Time/timeline, team members, and the QP "Time" field are unknown.

---

## 8. Open items / next steps

- Decide on the fourth core data structure (see 7.1).
- Choose sort algorithm (merge vs quick) and define the priority score formula and weights.
- Enter topic in the Google Sheet.
- Split work among 3 students so each does both OOP and DS parts (proposed but not yet drafted).
- Draft: UML class diagram outline, pseudocode, complexity analysis (best/avg/worst, time and space), report outline (5-10 pages), viva prep.
- Offered but not yet produced: team task split and UML outline.
