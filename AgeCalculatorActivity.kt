import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgeCalcActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var monthOfBirthEditText: EditText
    private lateinit var yearOfBirthEditText: EditText
    private lateinit var currentDateEditText: EditText
    private lateinit var chooseTodayDate: AutoCompleteTextView
    private lateinit var chooseDob: AutoCompleteTextView
    private lateinit var currentMonthEditText: EditText
    private lateinit var currentYearEditText: EditText
    private lateinit var calculateAgeTextView: TextView
    private lateinit var calculateMonthTextView: TextView
    private lateinit var calculateDayTextView: TextView
    private lateinit var calculateButtonRelativeLayout: RelativeLayout
    private lateinit var binding: ActivityAgeCalcBinding
    private lateinit var downloadManager: DownloadManager
    private lateinit var downloadCompleteReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgeCalcBinding.inflate(layoutInflater)
        setContentView(binding.root)
 
        chooseTodayDate = findViewById(R.id.chooseTodayDate)
        chooseDob = findViewById(R.id.chooseDob)
        calculateAgeTextView = findViewById(R.id.calculateAgeTextView)
        calculateMonthTextView = findViewById(R.id.calculateMonthTextView)
        calculateDayTextView = findViewById(R.id.calculateDayTextView)
        calculateButtonRelativeLayout = findViewById(R.id.calculateButtonRelativeLayout)

        chooseTodayDate.setOnClickListener(this)
        chooseDob.setOnClickListener(this)
        calculateButtonRelativeLayout.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.chooseTodayDate -> {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    chooseTodayDate.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time))
                },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }
            R.id.chooseDob -> {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    chooseDob.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time))
                },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }
            R.id.calculateButtonRelativeLayout -> {
                // Extract selected dates from chooseDob and chooseTodayDate
                val dob = Calendar.getInstance().apply {
                    time = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(chooseDob.text.toString())
                }
                val current = Calendar.getInstance().apply {
                    time = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(chooseTodayDate.text.toString())
                }

                val difference = current.timeInMillis - dob.timeInMillis
                val dayDifference = (difference / (1000 * 60 * 60 * 24)).toInt()

                calculateAgeTextView.text = (dayDifference / 365).toString()
                calculateMonthTextView.text = (dayDifference % 365 / 30).toString()
                calculateDayTextView.text = (dayDifference % 365 % 30).toString()

                calculateAgeTextView.visibility = View.VISIBLE
                calculateMonthTextView.visibility = View.VISIBLE
                calculateDayTextView.visibility = View.VISIBLE
            }
        }
    }
}
