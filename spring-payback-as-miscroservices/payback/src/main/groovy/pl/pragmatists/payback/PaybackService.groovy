package pl.pragmatists.payback

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.pragmatists.payback.purchase.Purchase
import pl.pragmatists.payback.remote.IntegrationClient

import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32

@Service
class PaybackService {

    @Autowired
    PaybackRepository paybackRepository
    @Autowired
    IntegrationClient integrationClient

    Iterable<Payback> findAll() {
        return paybackRepository.findAll()
    }

    void addPaybackFor(Purchase purchase) {
        BigDecimal paybackAmount = paybackAmountFor(purchase)
        PaybackReceiver receiver = integrationClient.findReceiverByCreditCard(purchase.creditCardNumber)
        paybackRepository.save(new Payback(
            customerId: receiver.id,
            amount: paybackAmount
        ))
    }

    BigDecimal paybackAmountFor(Purchase purchase) {
        PaybackGiver giver = integrationClient.findGiverMerchantById(purchase.merchantId)
        return Optional.<Purchase> of(purchase)
            .filter { it -> giver.percentage != null && it.amount != null }
            .filter { it -> giver.minAmount == null || giver.minAmount.compareTo(it.amount) <= 0 }
            .map { it -> giver.percentage.multiply(it.amount, DECIMAL32) }
            .map { it -> giver.maxPayback != null && giver.maxPayback.compareTo(it) < 0 ? giver.maxPayback : it }
            .orElse(ZERO);
    }
}
