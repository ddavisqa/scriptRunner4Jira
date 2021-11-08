import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def sb = new StringBuffer()

customFieldManager.getCustomFieldObjects().each{
    // Remove Custom Fields created by JIM importer
    // when importing a new project with JSON file
    if (it.getDescription() != null)
    {
        if(it.getDescription().contains("created by JIM during import process"))
        {
            sb.append("Remove CustomField: ${it.getName()}, ${it.getDescription()}\n<br>")
            customFieldManager.removeCustomField(it)
        }
    }
}

return sb
