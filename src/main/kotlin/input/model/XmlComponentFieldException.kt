package input.model

class XmlComponentFieldException(name: String, data: String?, className: String) :
    Exception("Invalid value for the '$name' attribute at the $className class: $data") {
}

class XmlComponentAttributeException(index: Int, data: String?, className: String, details: String? = null) :
    Exception("Invalid value for the attribute #$index at the $className class: $data${if (details != null) "\n$details" else ""}") {
}
