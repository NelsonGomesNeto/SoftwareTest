import requests
import json

def jsonStr(js):
    return(json.dumps(js, indent=2, sort_keys=True))

baseURL = "http://localhost:3000/academico/"
baseHeader = {"content-type": "application/json"}

person = json.dumps({"firstName": "Nelson", "lastName": "Gomes Neto", "studyingId": 2})
subject = json.dumps({"name": "P1", "code": "miau", "credits": 69, "requiredCredits": 0, "requiredSubjectsIds": []})
course = json.dumps({"name": "Computer Science", "subjectsIds": [1]})
secretary = json.dumps({"degreeLevel": "graduate", "coursesIds": [2]})
department = json.dumps({"name": "IC", "graduateId": 3, "postgraduateId": -1})

print("\n\tSubject:")
print("POST:\n", jsonStr(requests.post(baseURL + "subject/", data=subject, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "subject/1").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "subject/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tCourse:")
print("POST:\n", jsonStr(requests.post(baseURL + "course/", data=course, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "course/2").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "course/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tSecretary:")
print("POST:\n", jsonStr(requests.post(baseURL + "secretary/", data=secretary, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "secretary/3").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "secretary/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tDepartment:")
print("POST:\n", jsonStr(requests.post(baseURL + "department/", data=department, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "department/4").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "department/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\tPerson:")
print("POST:\n", jsonStr(requests.post(baseURL + "person/", data=person, headers=baseHeader).json()), sep='')
response = requests.put(baseURL + "person/5/enroll/1")
print("PUT (enroll):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')
# print("PUT (complete):\n", requests.put(baseURL + "person/5/complete/1").json(), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "person/5").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "person/").json()), sep='')