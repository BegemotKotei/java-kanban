package manager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> receivedTasks;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
         this.receivedTasks = new HashMap<>();
    }

    static class Node {
        private Task task;
        private Node prev;
        private Node next;

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private void linkLast(Task task) {
        final Node newNode = new Node(tail, task, null);
        if(head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }
            if (node.next != null && node.prev != null) { //middle
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            if (node.next == null && node.prev !=null) { // tail
                tail = node.prev;
                node.prev.next = null;
            }
            if (node.prev == null && node.next != null) { // head
            head = node.next;
            node.next.prev = null;
            }
            if (node == tail && node == head) {
            tail = null;
            head = null;
        }
    }

    public List<Task> getTasksHistory() {
        List<Task> tasks = new ArrayList<>();
        Node currentNode = head;
        while (currentNode != null) {
            tasks.add(currentNode.getTask());
            currentNode = currentNode.next;
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        int id = task.getId();
        remove(id);
        linkLast(task);
        receivedTasks.put(id,tail);
    }

    @Override
    public void remove(int id) {
        Node node = receivedTasks.remove(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }
    @Override
    public List<Task> getHistory() {
        return getTasksHistory();
    }

}
