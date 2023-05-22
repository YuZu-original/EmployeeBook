import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EmployeeBook {
    private final Employee[] objects = new Employee[10];

    public Employee[] getObjects() {
        return objects;
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
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                objects[i] = new Employee(fullName, department, salary);
                return this;
            }
        }
        throw new RuntimeException("There is no place to add");
    }

    public EmployeeBook remove(int id) throws RuntimeException {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].getId() == id) {
                objects[i] = null;
                return this;
            }
        }
        throw new RuntimeException("Not found Employee with id=" + id);
    }

    public EmployeeBook remove(String fullName) throws RuntimeException {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].getFullName().equals(fullName)) {
                objects[i] = null;
                return this;
            }
        }
        throw new RuntimeException("Not found Employee with fullName=" + fullName);
    }

    public EmployeeBook remove(int id, String fullName) throws RuntimeException {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].getId() == id && objects[i].getFullName().equals(fullName)) {
                objects[i] = null;
                return this;
            }
        }
        throw new RuntimeException("Not found Employee with id=" + id + " and fullName=" + fullName);
    }

    public EmployeeBook updateSalaryByFullName(String fullName, int salary) throws RuntimeException {
        for (Employee object : objects) {
            if (object != null && object.getFullName().equals(fullName)) {
                object.setSalary(salary);
                return this;
            }
        }
        throw new RuntimeException("Not found Employee with fullName=" + fullName);
    }

    public EmployeeBook updateDepartmentByFullName(String fullName, int department) throws RuntimeException {
        for (Employee object : objects) {
            if (object != null && object.getFullName().equals(fullName)) {
                object.setDepartment(department);
                return this;
            }
        }
        throw new RuntimeException("Not found Employee with fullName=" + fullName);
    }

    public EmployeeBook printAll() {
        for (Employee object : objects) {
            System.out.println(object);
        }
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
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department) {
                System.out.println(object);
            }
        }
        return this;
    }

    public EmployeeBook printAllFullNames() {
        for (Employee object : objects) {
            if (object != null) {
                System.out.println(object.getFullName());
            }
        }
        return this;
    }

    public int getSalarySum() {
        int sum = 0;
        for (Employee object : objects) {
            if (object != null) {
                sum += object.getSalary();
            }
        }
        return sum;
    }

    public Employee getEmployeeWithMinSalary() {
        Employee min = null;
        for (Employee object : objects) {
            if (object != null && (min == null || object.getSalary() < min.getSalary())) {
                min = object;
            }
        }
        return min;
    }

    public Employee getEmployeeWithMaxSalary() {
        Employee max = null;
        for (Employee object : objects) {
            if (object != null && (max == null || object.getSalary() > max.getSalary())) {
                max = object;
            }
        }
        return max;
    }

    public double getSalaryAverage() {
        return (double) getSalarySum() / objects.length;
    }

    public EmployeeBook raiseSalaries(double percent) {
        for (Employee object : objects) {
            if (object != null) {
                object.setSalary((int) (object.getSalary() * (percent / 100d + 1)));
            }
        }
        return this;
    }

    public int getSalarySum(int department) {
        int sum = 0;
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department) {
                sum += object.getSalary();
            }
        }
        return sum;
    }

    public Employee getEmployeeWithMinSalary(int department) {
        Employee min = null;
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department && (min == null || object.getSalary() < min.getSalary())) {
                min = object;
            }
        }
        return min;
    }

    public Employee getEmployeeWithMaxSalary(int department) {
        Employee max = null;
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department && (max == null || object.getSalary() > max.getSalary())) {
                max = object;
            }
        }
        return max;
    }

    public double getSalaryAverage(int department) {
        int sum = 0, k = 0;
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department) {
                sum += object.getSalary();
                k++;
            }
        }
        return (double) sum / k;
    }

    public EmployeeBook raiseSalaries(int department, double percent) {
        for (Employee object : objects) {
            if (object != null && object.getDepartment() == department) {
                object.setSalary((int) (object.getSalary() * (percent / 100d + 1)));
            }
        }
        return this;
    }



    public EmployeeBook printEmployersWithSalaryLT(int value) {
        for (Employee object : objects) {
            if (object != null && object.getSalary() < value) {
                printEmployeeWithoutDepartment(object);
            }
        }
        return this;
    }

    public EmployeeBook printEmployersWithSalaryGE(int value) {
        for (Employee object : objects) {
            if (object != null && object.getSalary() >= value) {
                printEmployeeWithoutDepartment(object);
            }
        }
        return this;
    }

    public HashMap<Integer, List<Employee>> getEmployeesGroupingByDepartment() {
        HashMap<Integer, List<Employee>> hashMap = new HashMap<Integer, List<Employee>>();
        for (Employee object : objects) {
            if (object == null) {
                continue;
            }
            List<Employee> list = hashMap.get(object.getDepartment());

            if (list == null) {
                List<Employee> newList = new ArrayList<Employee>();
                newList.add(object);

                hashMap.put(object.getDepartment(), newList);
            } else {
                list.add(object);
            }
        }
        return hashMap;
    }

    public EmployeeBook printEmployeesGroupingByDepartment() {
        getEmployeesGroupingByDepartment().forEach((key, value) -> {
            System.out.println("department " + key + ":");
            value.forEach(x -> System.out.println(x.getFullName()));
        });
        return this;
    }
}
