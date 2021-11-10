import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.servicedesk.api.sla.info.SlaInformationService
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import com.onresolve.scriptrunner.runner.customisers.WithPlugin

@WithPlugin("com.atlassian.servicedesk")
@PluginModule SlaInformationService slaInformationService

// SLA field name
final slaName = '<SLA name>'

// Gets the SLA information querying SLA service for the current issue
def query = slaInformationService.newInfoQueryBuilder()
    .issue(issue.id)
    .build()
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def slaFormatter = slaInformationService.durationFormatter
def sla = slaInformationService.getInfo(user, query).results.find { it.name == slaName }

if (sla?.ongoingCycle?.present) {
    // If there is an ongoing SLA. it takes the current ongoing SLA remaining time and format it as duration of "X hours Y minutes"
    log.error("SLA remaining time: ${sla.ongoingCycle.get().remainingTime}")
    slaFormatter.format(user, sla.ongoingCycle.get().remainingTime)
} else {
    // If there is no ongoing SLA, it takes last completed cycle remaining type and format it as duration of "X hours Y minutes"
    log.error("SLA remaining time: ${sla?.completedCycles?.last()?.remainingTime}")
    slaFormatter.format(user, sla?.completedCycles?.last()?.remainingTime)
}
