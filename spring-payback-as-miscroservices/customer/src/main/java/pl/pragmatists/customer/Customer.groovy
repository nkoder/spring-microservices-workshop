package pl.pragmatists.customer

import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@ToString
class Customer {

    @Id
    @GeneratedValue
    Long id;

    String firstName

    String lastName

    String creditCard
}
