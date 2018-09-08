import requests
import json

baseURL = "http://localhost:3000/academico/"
baseHeader = {"content-type": "application/json"}

person = json.dumps({"firstName": "Nelson", "lastName": "Gomes Neto"})
subject = json.dumps({"name": "P1", "code": "miau", "credits": 69, "requiredCredits": 69, "requiredSubjectsIds": []})
course = json.dumps({"name": "Ciência da Computação", "subjectsIds": [2]})
secretary = json.dumps({"degreeLevel": "graduate", "coursesIds": [3]})
department = json.dumps({"name": "IC", "graduateId": 4, "postgraduateId": -1})

print("Person:")
print("\tPOST:", requests.post(baseURL + "person/", data=person, headers=baseHeader).json())
print("\tGET id:", requests.get(baseURL + "person/1").json())
print("\tGET all:", requests.get(baseURL + "person/").json())
# print("DELETE:", re

print("\nSubject:")
print("\tPOST:", requests.post(baseURL + "subject/", data=subject, headers=baseHeader).json())
print("\tGET id:", requests.get(baseURL + "subject/2").json())
print("\tGET all:", requests.get(baseURL + "subject/").json())

print("\nCourse:")
print("\tPOST:", requests.post(baseURL + "course/", data=course, headers=baseHeader).json())
print("\tGET id:", requests.get(baseURL + "course/3").json())
print("\tGET all:", requests.get(baseURL + "course/").json())

print("\nSecretary:")
print("\tPOST:", requests.post(baseURL + "secretary/", data=secretary, headers=baseHeader).json())
print("\tGET id:", requests.get(baseURL + "secretary/4").json())
print("\tGET all:", requests.get(baseURL + "secretary/").json())

print("\nDepartment:")
print("\tPOST:", requests.post(baseURL + "department/", data=department, headers=baseHeader).json())
print("\tGET id:", requests.get(baseURL + "department/5").json())
print("\tGET all:", requests.get(baseURL + "department/").json())