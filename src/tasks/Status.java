package tasks;


public enum Status {
    NEW("NEW"), IN_PROGRESS("IN_PROGRESS"), DONE("DONE");

    final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
