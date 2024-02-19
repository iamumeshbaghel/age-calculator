
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ubproject.kotlinpoint.databinding.ActivityEmicalcBinding
import kotlin.math.pow

class EMICalcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmicalcBinding
    private lateinit var principleEditText: EditText
    private lateinit var interestEditText: EditText
    private lateinit var loanTenureYearsEditText: EditText
    private lateinit var loanTenureMonthsEditText: EditText
    private lateinit var emiTextView: TextView
    private lateinit var totalPaymentTextView: TextView
    private lateinit var totalPrincipleTextView: TextView
    private lateinit var totalInterestTextView: TextView
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmicalcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        principleEditText = findViewById(R.id.principle_edittext)
        interestEditText = findViewById(R.id.interest_edittext)
        loanTenureYearsEditText = findViewById(R.id.years_edittext)
        loanTenureMonthsEditText = findViewById(R.id.months_edittext)
        emiTextView = findViewById(R.id.emi_textview)
        totalPaymentTextView = findViewById(R.id.total_payment_textview)
        totalPrincipleTextView = findViewById(R.id.total_principle_textview)
        totalInterestTextView = findViewById(R.id.total_interest)
        calculateButton = findViewById(R.id.calculate_button)

        // Setting click listener for the calculate button
        calculateButton.setOnClickListener {
            // Getting values from the input fields
            val principle = principleEditText.text.toString().toDouble()
            val interest = interestEditText.text.toString().toDouble()
            val loanTenureYears = loanTenureYearsEditText.text.toString().toInt()
            val loanTenureMonths = loanTenureMonthsEditText.text.toString().toInt()

            // Calculating loan tenure in months
            val loanTenureInMonths = loanTenureYears * 12 + loanTenureMonths

            // Calculating monthly interest rate and EMI
            val monthlyInterestRate = interest / (12 * 100)
            val emi = calculateEMI(principle, monthlyInterestRate, loanTenureInMonths)

            // Calculating total payment, total principle, and total interest
            val totalPayment = emi * loanTenureInMonths
            val totalInterest = totalPayment - principle

            // Setting result text in the respective TextViews
            emiTextView.text = String.format("₹%.2f", emi)
            totalPaymentTextView.text = String.format("₹%.2f", totalPayment)
            totalPrincipleTextView.text = String.format("₹%.2f", principle)
            totalInterestTextView.text = String.format("₹%.2f", totalInterest)
        }
    }

    /**
     * Calculates the EMI based on the principle, monthly interest rate, and loan tenure in months.
     */
    private fun calculateEMI(principle: Double, monthlyInterestRate: Double, loanTenureInMonths: Int): Double {
        val numerator = principle * monthlyInterestRate * (1 + monthlyInterestRate).pow(loanTenureInMonths)
        val denominator = (1 + monthlyInterestRate).pow(loanTenureInMonths) - 1
        return numerator / denominator
    }
}
