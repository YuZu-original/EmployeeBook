import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EmployeeBook {
    private final Employee[] objects = new Employee[10];

    public Employee[] getObjects() {
        return objects;
    }


    private IntStream getIndexesStream() {
        return IntStream.range(0, objects.length);
    }

    private Stream<Employee> getObjectsStream() {
        return Arrays.stream(objects);
    }

    private int getObjIndexByFullName(String fullName) {
        for (int i = 0; i < objects.length; i++) {
            Employee obj = objects[i];
            if (obj != null && obj.getFullName().equals(fullName)) {
                return i;
            }
        }
        return -1;
    }

    public EmployeeBook add(String fullName, int department, int salary) throws RuntimeException {
        int index = getIndexesStream()
                .filter(i -> objects[i] == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no place to add"));
        objects[index] = new Employee(fullName, department, salary);
        return this;
    }

    public EmployeeBook remove(int id) throws RuntimeException {
        int index = getIndexesStream()
                .filter(i -> objects[i] != null && objects[i].getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Employee with id=" + id));
        objects[index] = null;
        return this;
    }

    public EmployeeBook remove(String fullName) throws RuntimeException {
        int index = getIndexesStream()
                .filter(i -> objects[i] != null && objects[i].getFullName().equals(fullName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Employee with fullName=" + fullName));
        objects[index] = null;
        return this;
    }

    public EmployeeBook remove(int id, String fullName) throws RuntimeException {
        int index = getIndexesStream()
                .filter(i -> objects[i] != null && objects[i].getId() == id && objects[i].getFullName().equals(fullName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Employee with id=" + id + " and fullName=" + fullName));
        objects[index] = null;
        return this;
    }

    public EmployeeBook updateSalaryByFullName(String fullName, int salary) throws RuntimeException {
        Employee emp = getObjectsStream()
                .filter(x -> x != null && x.getFullName().equals(fullName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Employee with fullName=" + fullName));
        emp.setSalary(salary);
        return this;
    }

    public EmployeeBook updateDepartmentByFullName(String fullName, int department) throws RuntimeException {
        Employee emp = getObjectsStream()
                .filter(x -> x != null && x.getFullName().equals(fullName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Employee with fullName=" + fullName));
        emp.setDepartment(department);
        return this;
    }

    public EmployeeBook printAll() {
        getObjectsStream().forEach(System.out::println);
        return this;
    }

    private static void printEmployeeWithoutDepartment(Employee emp) {
        System.out.println("Employee{" +
                "id=" + emp.getId() +
                ", fullName='" + emp.getFullName() + '\'' +
                ", salary=" + emp.getSalary() +
                '}');
    }

    public EmployeeBook printByDepartment(int department) {
        getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .forEach(EmployeeBook::printEmployeeWithoutDepartment);
        return this;
    }

    public EmployeeBook printAllFullNames() {
        getObjectsStream()
                .filter(Objects::nonNull)
                .forEach(x -> System.out.println(x.getFullName()));
        return this;
    }

    public int getSalarySum() {
        return getObjectsStream()
                .filter(Objects::nonNull)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMinSalary() {
        return getObjectsStream()
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public Employee getEmployeeWithMaxSalary() {
        return getObjectsStream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public double getSalaryAverage() {
        return getObjectsStream()
                .filter(Objects::nonNull)
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0d);
    }

    public EmployeeBook raiseSalaries(double percent) {
        getObjectsStream()
                .filter(Objects::nonNull)
                .forEach(x -> x.setSalary((int) (x.getSalary() * (percent / 100d + 1))));
        return this;
    }

    public int getSalarySum(int department) {
        return getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMinSalary(int department) {
        return getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public Employee getEmployeeWithMaxSalary(int department) {
        return getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public double getSalaryAverage(int department) {
        return getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0d);
    }

    public EmployeeBook raiseSalaries(int department, double percent) {
        getObjectsStream()
                .filter(x -> x != null && x.getDepartment() == department)
                .forEach(x -> x.setSalary((int) (x.getSalary() * (percent / 100d + 1))));
        return this;
    }

    public Employee[] getEmployersWithSalaryLT(int value) {
        return getObjectsStream().filter(x -> x != null && x.getSalary() < value).toArray(Employee[]::new);
    }

    public Employee[] getEmployersWithSalaryGE(int value) {
        return getObjectsStream().filter(x -> x != null && x.getSalary() >= value).toArray(Employee[]::new);
    }

    public void printEmployersWithSalaryLT(int value) {
        Arrays.stream(getEmployersWithSalaryLT(value))
                .forEach(EmployeeBook::printEmployeeWithoutDepartment);
    }

    public void printEmployersWithSalaryGE(int value) {
        Arrays.stream(getEmployersWithSalaryGE(value))
                .forEach(EmployeeBook::printEmployeeWithoutDepartment);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupingByDepartment() {
        return getObjectsStream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public void printEmployeesGroupingByDepartment() {
        getEmployeesGroupingByDepartment().entrySet().stream().forEach(x -> {
            System.out.println("department " + x.getKey().toString() + ":");
            String[] fullNames = x.getValue().stream().map(Employee::getFullName).toArray(String[]::new);
            System.out.println(String.join("\n", fullNames));
        });
    }
}
