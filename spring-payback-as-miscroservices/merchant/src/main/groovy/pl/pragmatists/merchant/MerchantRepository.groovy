package pl.pragmatists.merchant

import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
