package com.sporecomerce.api.demo.model;

import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {
    /*
     * private static final int NUM_PERSONS = 100; private static final int
     * NUM_COMPANIES = 100; private static final int ROLES_PER_COMPANY = 3; private
     * static final int EMPLOYEES_PER_ROLE_PER_COMPANY = 4;
     * 
     * /*
     * 
     * @Autowired CompanyRepository companyRepository;
     * 
     * @Autowired DivisionRepository divisionRepository;
     * 
     * @Autowired RoleRepository roleRepository;
     */
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        /*
         * Random random = new Random(1234);
         * 
         * RandomStringGenerator randomGen = new
         * RandomStringGenerator.Builder().withinRange('a', 'z')
         * .usingRandom(random::nextInt).build();
         * 
         * for (int i = 0; i < NUM_PERSONS; i++) { String name = randomGen.generate(5,
         * 10); int ssn = random.nextInt(1000000); personRepository.save(new
         * Person(name, ssn)); }
         * 
         * for (int i = 0; i < NUM_COMPANIES; i++) { String name = randomGen.generate(5,
         * 10); String tid = randomGen.generate(5, 10); companyRepository.save(new
         * Company(name, tid)); }
         * 
         * for (Company company : companyRepository.findAll()) { for (int j = 0; j <
         * EMPLOYEES_PER_ROLE_PER_COMPANY; j++) { for (int i = 0; i < ROLES_PER_COMPANY;
         * i++) { try { Person p = personRepository.findById(Math.abs(random.nextLong())
         * % NUM_PERSONS + 1) .orElseThrow(); roleRepository.save(new Rol(company, p,
         * "role")); } catch (NoSuchElementException e) { e.printStackTrace(); } } } }
         */

    }

}
