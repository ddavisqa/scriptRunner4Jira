import com.atlassian.jira.component.ComponentAccessor

def jpField = getFieldByName("JIRA Project")
def urlField = getFieldByName("GitLab URL")
def aurlField = getFieldByName("Additional URL's / Notes")

def fvField = getFieldById("fixVersions")
def sField = getFieldByName("Sprint")
def tField = getFieldById("timetracking_originalestimate")
def rField = getFieldById("timetracking_remainingestimate")
def SPField = getFieldByName("Story Points")

def crField = getFieldById(getFieldChanged())
def selectedOption = crField.getValue() as String

if ((selectedOption.contains("GIT Repositories")))
{
    urlField.setHidden(false)
    urlField.setRequired(true)
    aurlField.setHidden(false)
    aurlField.setRequired(true)
    jpField.setHidden(false)
    jpField.setRequired(true)
    
    fvField.setHidden(true)
    sField.setHidden(true)
    tField.setHidden(true)
    rField.setHidden(true)
    SPField.setHidden(true)
  
}
else
{
    urlField.setHidden(true)
    urlField.setRequired(false)
    aurlField.setHidden(true)
    aurlField.setRequired(false)
    jpField.setHidden(true)
    jpField.setRequired(false)
    
    fvField.setHidden(true)
    sField.setHidden(false)
    tField.setHidden(true)
    rField.setHidden(true)
    SPField.setHidden(true)
}
