import requests
import json

def jsonStr(js):
    return(json.dumps(js, indent=2, sort_keys=True))

baseURL = "http://localhost:3000/academico/"
baseHeader = {"content-type": "application/json"}

student = json.dumps({"firstName": "Nelson", "lastName": "Gomes Neto", "studyingId": 2})
subject = json.dumps({"name": "P1", "credits": 69, "requiredCredits": 0, "requiredSubjectsIds": [], "degreeLevel": "graduate", "professor": "Willy Tiengo"})
subjects = [json.dumps({"name": "P2", "credits": 69, "requiredCredits": 69, "requiredSubjectsIds": [1], "degreeLevel": "graduate", "professor": "Willy Tiengo"}),
            json.dumps({"name": "P3", "credits": 69, "requiredCredits": 0, "requiredSubjectsIds": [1, 2], "degreeLevel": "graduate", "professor": "Willy Tiengo"}),
            json.dumps({"name": "P4", "credits": 69, "requiredCredits": 0, "requiredSubjectsIds": [1, 2, 3], "degreeLevel": "graduate", "professor": "Willy Tiengo"})]
course = json.dumps({"name": "Computer Science", "subjectsIds": [1, 2, 3, 4], "degreeLevel": "graduate"})
secretary = json.dumps({"degreeLevel": "graduate", "coursesIds": [5]})
department = json.dumps({"name": "IC", "graduateId": 6, "postgraduateId": -1})

print("\n\tSubject:")
print("POST:\n", jsonStr(requests.post(baseURL + "subject/", data=subject, headers=baseHeader).json()), sep='')
for s in subjects: requests.post(baseURL + "subject/", data=s, headers=baseHeader)
print("GET id:\n", jsonStr(requests.get(baseURL + "subject/1").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "subject/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tCourse:")
print("POST:\n", jsonStr(requests.post(baseURL + "course/", data=course, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "course/5").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "course/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tSecretary:")
print("POST:\n", jsonStr(requests.post(baseURL + "secretary/", data=secretary, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "secretary/6").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "secretary/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\n\tDepartment:")
print("POST:\n", jsonStr(requests.post(baseURL + "department/", data=department, headers=baseHeader).json()), sep='')
print("GET id:\n", jsonStr(requests.get(baseURL + "department/7").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "department/").json()), sep='')

print("---------------------------------------------------------------------------------------------------------")

print("\tStudent:")
print("POST:\n", jsonStr(requests.post(baseURL + "student/", data=student, headers=baseHeader).json()), sep='')
response = requests.put(baseURL + "student/8/enrollSubject/1")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')

response = requests.put(baseURL + "student/8/enrollCourse/5")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')

response = requests.put(baseURL + "student/8/enrollSubject/1")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')
response = requests.put(baseURL + "student/8/enrollSubject/2")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')
response = requests.put(baseURL + "student/8/enrollSubject/3")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')

print("PUT (complete):\n", jsonStr(requests.put(baseURL + "student/8/complete/1").json()), sep='')
response = requests.put(baseURL + "student/8/enrollSubject/2")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')

print("PUT (complete):\n", jsonStr(requests.put(baseURL + "student/8/complete/2").json()), sep='')
response = requests.put(baseURL + "student/8/enrollSubject/3")
print("PUT (enroll subject):\n", (response.status_code, response.text) if response.status_code != 200 else jsonStr(response.json()), sep='')

print("GET id:\n", jsonStr(requests.get(baseURL + "student/8").json()), sep='')
print("GET all:\n", jsonStr(requests.get(baseURL + "student").json()), sep='')
print("GET subjectsFromDepartment:\n", jsonStr(requests.get(baseURL + "student/8/subjectsFromDepartment").json()), sep='')

# print("GET all:\n", jsonStr(requests.get(baseURL + "department/").json()))

print("---------------------------------------------------------------------------------------------------------")
print("\tFunctional Requirements")
print("GET allName:\n", jsonStr(requests.get(baseURL + "student/names").json()), sep='') # 1 list name of every student
print("GET getStudentsDepartmentSubjects:\n", jsonStr(requests.get(baseURL + "student/8/subjectsFromDepartment").json()), sep='') # 1 list subjects of student's department
# 1 and 2: all constraints to enrollment were covered above on "Student" section

print("GET proofOfEnrollment:\n", jsonStr(requests.get(baseURL + "student/8/proofOfEnrollment").json()), sep='') # 3C
print("GET schedule:\n", jsonStr(requests.get(baseURL + "subject/3/schedule").json()), sep='') # 3B
print("GET notDeep:\n", jsonStr(requests.get(baseURL + "department/notDeep").json()), sep='') # 3A