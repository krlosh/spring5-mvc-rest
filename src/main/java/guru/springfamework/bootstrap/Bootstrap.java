package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.loadCategories();
        this.loadCustomers();
        this.loadVendors();
        System.out.println("Data categories loaded " + this.categoryRepository.count());
        System.out.println("Data customers loaded " + this.customerRepository.count());
        System.out.println("Data vendors loaded " + this.vendorRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        this.categoryRepository.save(fruits);
        this.categoryRepository.save(dried);
        this.categoryRepository.save(fresh);
        this.categoryRepository.save(exotic);
        this.categoryRepository.save(nuts);
    }

    private void loadCustomers() {
        Customer first = new Customer();
        first.setFirstName("John");
        first.setLastName("Foo");

        Customer second = new Customer();
        second.setFirstName("Michael");
        second.setLastName("Woods");
        this.customerRepository.save(first);
        this.customerRepository.save(second);
    }

    private void loadVendors() {
        Vendor firstVendor = new Vendor();
        firstVendor.setName("First vendor");

        Vendor secondVendor = new Vendor();
        secondVendor.setName("First vendor");

        this.vendorRepository.save(firstVendor);
        this.vendorRepository.save(secondVendor);
    }
}
