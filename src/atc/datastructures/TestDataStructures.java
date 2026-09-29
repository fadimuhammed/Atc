package atc.datastructures;

import atc.model.*;
import java.time.LocalDateTime;

public class TestDataStructures {
    public static void main(String[] args) {
        System.out.println("=== Phase 2 Data Structures Test ===\n");

        testStack();
        testCircularQueue();
        testArrayPriorityQueue();
        testHashTable();
        testSortedArrayRegistry();
        testMergeSort();

        System.out.println("\n=== All Data Structure Tests Passed ===");
    }

    private static void testStack() {
        System.out.println("--- Test: Stack ---");

        Stack stack = new Stack();
        assert stack.isEmpty() : "New stack should be empty";
        assert stack.size() == 0 : "New stack size should be 0";

        stack.push("Flight1");
        stack.push("Flight2");
        stack.push("Flight3");
        assert stack.size() == 3 : "Stack size should be 3";
        assert "Flight3".equals(stack.peek()) : "Top should be Flight3";

        String popped = (String) stack.pop();
        assert "Flight3".equals(popped) : "Popped should be Flight3";
        assert stack.size() == 2 : "Stack size should be 2 after pop";

        popped = (String) stack.pop();
        assert "Flight2".equals(popped) : "Popped should be Flight2";
        popped = (String) stack.pop();
        assert "Flight1".equals(popped) : "Popped should be Flight1";

        assert stack.isEmpty() : "Stack should be empty";

        System.out.println("Stack: OK\n");
    }

    private static void testCircularQueue() {
        System.out.println("--- Test: Circular Queue ---");

        CircularQueue queue = new CircularQueue(3);
        assert queue.isEmpty() : "New queue should be empty";
        assert queue.size() == 0 : "New queue size should be 0";

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        assert queue.isFull() : "Queue should be full";
        assert queue.size() == 3 : "Queue size should be 3";

        String dequeued = (String) queue.dequeue();
        assert "A".equals(dequeued) : "Dequeued should be A";
        assert queue.size() == 2 : "Queue size should be 2";

        queue.enqueue("D");
        assert queue.size() == 3 : "Queue size should be 3 again";

        dequeued = (String) queue.dequeue();
        assert "B".equals(dequeued) : "Dequeued should be B";
        dequeued = (String) queue.dequeue();
        assert "C".equals(dequeued) : "Dequeued should be C";
        dequeued = (String) queue.dequeue();
        assert "D".equals(dequeued) : "Dequeued should be D";

        assert queue.isEmpty() : "Queue should be empty";

        System.out.println("Circular Queue: OK\n");
    }

    private static void testArrayPriorityQueue() {
        System.out.println("--- Test: Array Priority Queue ---");

        PriorityCalculator calc = new PriorityCalculator();
        ArrayPriorityQueue pq = new ArrayPriorityQueue(10, calc);

        CommercialFlight f1 = new CommercialFlight("A1", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("A2", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 30, false);
        EmergencyFlight f3 = new EmergencyFlight("E1", "BOM", "DEL",
                LocalDateTime.now().plusMinutes(20), 20);

        pq.enqueue(f1);
        pq.enqueue(f2);
        pq.enqueue(f3);

        Aircraft peeked = pq.peek();
        assert peeked.getFlightId().equals("E1") : "Emergency should be first";

        Aircraft dequeued = pq.dequeue();
        assert dequeued.getFlightId().equals("E1") : "Emergency dequeued first";

        Aircraft next = pq.dequeue();
        assert next.getFlightId().equals("A2") : "A2 should be next (lower fuel)";

        Aircraft last = pq.dequeue();
        assert last.getFlightId().equals("A1") : "A1 should be last";

        System.out.println("Array Priority Queue: OK\n");
    }

    private static void testHashTable() {
        System.out.println("--- Test: Hash Table ---");

        HashTable table = new HashTable(4);

        CommercialFlight f1 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 60, false);
        CommercialFlight f3 = new CommercialFlight("AI103", "BOM", "DEL",
                LocalDateTime.now().plusHours(3), 40, false);

        table.put("AI101", f1);
        table.put("AI102", f2);
        table.put("AI103", f3);

        assert table.size() == 3 : "Table size should be 3";
        assert table.get("AI101").equals(f1) : "AI101 should be found";
        assert table.get("AI102").equals(f2) : "AI102 should be found";
        assert table.containsKey("AI103") : "AI103 should exist";
        assert !table.containsKey("AI999") : "Non-existent key should not be found";

        Aircraft removed = (Aircraft) table.remove("AI102");
        assert removed.equals(f2) : "Removed aircraft should be AI102";
        assert table.size() == 2 : "Table size should be 2 after removal";
        assert table.get("AI102") == null : "AI102 should be gone";

        table.put("AI101", f1);
        assert table.size() == 2 : "Size should remain 2 after updating existing key";

        System.out.println("Hash Table: OK\n");
    }

    private static void testSortedArrayRegistry() {
        System.out.println("--- Test: Sorted Array Registry ---");

        SortedArrayRegistry registry = new SortedArrayRegistry(10);

        CommercialFlight f1 = new CommercialFlight("AI103", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 60, false);
        CommercialFlight f3 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(3), 40, false);

        registry.insert(f1);
        registry.insert(f2);
        registry.insert(f3);

        assert registry.size() == 3 : "Registry size should be 3";

        Aircraft found = registry.find("AI102");
        assert found.equals(f3) : "AI102 should be found";

        Aircraft[] range = registry.rangeQuery("AI101", "AI102");
        assert range.length == 2 : "Range query should return 2 aircraft";
        assert range[0].getFlightId().equals("AI101");
        assert range[1].getFlightId().equals("AI102");

        Aircraft removed = registry.remove("AI101");
        assert removed.equals(f2) : "Removed aircraft should be AI101";
        assert registry.size() == 2 : "Registry size should be 2";

        System.out.println("Sorted Array Registry: OK\n");
    }

    private static void testMergeSort() {
        System.out.println("--- Test: Merge Sort ---");

        CommercialFlight f1 = new CommercialFlight("AI103", "BOM", "DEL",
                LocalDateTime.now().plusHours(2), 50, false);
        CommercialFlight f2 = new CommercialFlight("AI101", "BOM", "DEL",
                LocalDateTime.now().plusHours(1), 60, false);
        CommercialFlight f3 = new CommercialFlight("AI102", "BOM", "DEL",
                LocalDateTime.now().plusHours(3), 40, false);

        Aircraft[] array = {f1, f2, f3};
        SortAlgorithms.mergeSort(array);

        assert array[0].getFlightId().equals("AI101");
        assert array[1].getFlightId().equals("AI102");
        assert array[2].getFlightId().equals("AI103");

        // Test sort by priority
        PriorityCalculator calc = new PriorityCalculator();
        Aircraft[] priorityArray = {f1, f2, f3};
        SortAlgorithms.mergeSortByPriority(priorityArray, calc);

        System.out.println("Sorted by priority:");
        for (Aircraft a : priorityArray) {
            System.out.println("  " + a.getFlightId() + " score=" + a.getPriorityScore(calc));
        }

        System.out.println("Merge Sort: OK\n");
    }
}