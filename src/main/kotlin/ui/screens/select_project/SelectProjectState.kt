package ui.screens.select_project

sealed class SelectProjectState()

class SelectProjectInitial : SelectProjectState()
class SelectProjectLoading : SelectProjectState()
class SelectProjectError : SelectProjectState()
class SelectProjectSuccess : SelectProjectState()
