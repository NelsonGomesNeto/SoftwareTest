import requests
import json

def jsonStr(js):
    return(json.dumps(js, indent=2, sort_keys=True))

baseURL = "http://localhost:3000/academico/"
baseHeader = {"content-type": "application/json"}

person = json.dumps({"firstName": "Nelson", "lastName": "Gomes Neto"})
subject = json.dumps({"name": "P1", "code": "miau", "credits": 69, "requiredCredits": 69, "requiredSubjectsIds": []})
course = json.dumps({"name": "Computer Science", "subjectsIds": [2]})
secretary = json.dumps({"degreeLevel": "graduate", "coursesIds": [3]})
department = json.dumps({"name": "IC", "graduateId": 4, "postgraduateId": -1})

print("\tPerson:")
print("POST:\n", jsonStr(requests.post(baseURL + "person/", data=person, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "person/1").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "person/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tSubject:")
print("POST:\n", jsonStr(requests.post(baseURL + "subject/", data=subject, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "subject/2").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "subject/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tCourse:")
print("POST:\n", jsonStr(requests.post(baseURL + "course/", data=course, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "course/3").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "course/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tSecretary:")
print("POST:\n", jsonStr(requests.post(baseURL + "secretary/", data=secretary, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "secretary/4").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "secretary/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tDepartment:")
print("POST:\n", jsonStr(requests.post(baseURL + "department/", data=department, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "department/5").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "department/").json()), sep='')