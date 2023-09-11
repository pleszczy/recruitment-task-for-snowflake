package pl.snowflake;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Objects;

record Person(String id, String name, String companyId) {
}

record Company(String id, String name) {
}

record Result(String personId, String personName, String companyName) {
}

public class Solution {
  private static List<Person> getAllPersons() {
    var p1 = new Person("p1", "Mariusz", "c1");
    var p2 = new Person("p2", "Dariusz", null);
    var p3 = new Person("p3", "Dariusz", "c2");
    var p4 = new Person("p4", "Dariusz Drugi", "c2");
    return List.of(p1, p2, p3, p4);
  }

  private static List<Company> getAllCompanies() {
    var c1 = new Company("c1", "Company 1");
    var c2 = new Company("c2", "Company 2");
    var c3 = new Company("c3", "Company 3");
    return List.of(c1, c2, c3);
  }

  public static void main(String[] args) {
    System.out.println(executeQuery());
  }

  /**
   * O(m+n) solution.
   *
   * @return results
   */
  private static List<Result> executeQuery() {
    var companies = getAllCompanies();
    var persons = getAllPersons();
    var companyIdToCompanyName =
        companies.stream().collect(toMap(Company::id, Company::name, (a, b) -> b));

    return persons.stream()
        .filter(person -> Objects.equals(person.name(), "Dariusz") && person.companyId() != null)
        .map(it -> new Result(it.id(), it.name(), companyIdToCompanyName.get(it.companyId())))
        .toList();
  }
}
