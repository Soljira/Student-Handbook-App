import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Event(
    val title: String,
    val description: String,
    val month: String,
    val day: String,
    val date: LocalDate // Actual date of the event
) {
    companion object {
        // Convert "Feb 10" to LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromString(title: String, description: String, dateString: String): Event {
            val formatter = DateTimeFormatter.ofPattern("MMM d yyyy")
            val date = LocalDate.parse("$dateString ${LocalDate.now().year}", formatter)
            return Event(title, description, date.month.name.capitalize(), date.dayOfMonth.toString(), date)
        }
    }
}
