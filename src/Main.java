public class Main {
    public static void main(String[] args) {
        EmployeeBook employeeBook = new EmployeeBook();
        employeeBook
                .add("Yu Zu", 3, 100_000)
                .add("Super Worker", 4, 1_000_000)
                .add("Noob", 1, 50_000)
                .add("Pro", 1, 200_000)
                .add("Vasya Pupkin", 2, 70_000)
                .add("Random man", 2, 90_000);

        employeeBook.printAll();
        System.out.println(employeeBook.getSalarySum());
        System.out.println(employeeBook.getEmployeeWithMinSalary());
        System.out.println(employeeBook.getEmployeeWithMaxSalary());
        System.out.println(employeeBook.getSalaryAverage());
        employeeBook.printAllFullNames();

        employeeBook
                .raiseSalaries(50)
                .printAll();
        System.out.println(employeeBook.getSalarySum(2));
        System.out.println(employeeBook.getEmployeeWithMinSalary(2));
        System.out.println(employeeBook.getEmployeeWithMaxSalary(2));
        System.out.println(employeeBook.getSalaryAverage(2));
        employeeBook.printByDepartment(2);
        System.out.println("--------");
        employeeBook.printEmployersWithSalaryLT(100_000);
        System.out.println("--------");
        employeeBook.printEmployersWithSalaryGE(100_000);
        System.out.println("--------");

        employeeBook
                .remove(1)
                .remove("Noob")
                .remove(0, "Yu Zu")
                .printAll();
        System.out.println("--------");

        employeeBook
                .add("New man", 2, 400_000)
                .printAll();

        System.out.println("--------");
        employeeBook
                .updateSalaryByFullName("New man", 500_000)
                .updateDepartmentByFullName("New man", 3)
                .printAll();
        System.out.println("--------");
        employeeBook.printEmployeesGroupingByDepartment();
    }


}