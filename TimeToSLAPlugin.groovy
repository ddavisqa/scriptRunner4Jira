import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.customfields.CustomFieldType
import com.atlassian.jira.issue.fields.CustomField
import java.util.*
import java.text.SimpleDateFormat
import java.sql.Timestamp
  
// get CustomFieldManager instance
def customFieldManager = ComponentAccessor.getCustomFieldManager()
// find your TTS custom field's ID and put it here instead of 10600
def ttsField = customFieldManager.getCustomFieldObject("customfield_10600")
// get custom field value object
def ttsFieldValue = issue.getCustomFieldValue(ttsField)
// date/time formatter will be used to format date attributes
def formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
// all attributes will be stored to result variable
def result = ""
// TTS custom field returns list, let's iterate
if (ttsFieldValue && ttsFieldValue.size() > 0) {
   ttsFieldValue.each {
       String slaName = it.slaName
       int slaValueAsMinutes = it.slaValue
       String slaValueAsTimeString = it.slaValueAsString
       String originStatusName = it.originStatusName
       String targetStatusName = it.targetStatusName
       Date originDate = it.statusDate
       Date expectedTargetDate = it.slaTargetDate
       Date actualTargetDate = it.untilDate
       long timeLeftTillSla = it.timeToSla // if less than 0, overdue
       String timeLeftTillSlaAsTimeString = it.timeToSlaAsString // if there is overdue, overdue string
       boolean isPaused = it.paused
       boolean startDateProvidedByDateCustomField = it.startDateProvidedByDateCustomField
       boolean endDateProvidedByDateCustomField = it.endDateProvidedByDateCustomField
       boolean negotiationDateProvidedByDateCustomField = it.negotiationDateProvidedByDateCustomField
  
       result = """
          [SLA Name: $slaName]
          [SLA Value As Minutes: $slaValueAsMinutes]
          [SLA Value As Time String: $slaValueAsTimeString]
          [Origin Status: $originStatusName]
          [Target Status: $targetStatusName]
          [Origin Date: ${formatter.format(originDate)}]
          [Expected Target Date: ${expectedTargetDate ? formatter.format(expectedTargetDate) : 'Not yet defined'}]
          [Actual Target Date: ${actualTargetDate ? formatter.format(actualTargetDate) : 'Not yet'}]
          [Time Left Till SLA as milis: $timeLeftTillSla]
          [Time Left Till SLA as Time String: $timeLeftTillSlaAsTimeString]
          [Is SLA in Paused Status: $isPaused]
          [Is Start Date Provided By Date Custom Field: $startDateProvidedByDateCustomField]
          [Is End Date Provided By Date Custom Field: $endDateProvidedByDateCustomField]
          [Is Negotiation Date Provided By Date Custom Field: $negotiationDateProvidedByDateCustomField]
          <br>
       """
     }
}
  
result.toString()