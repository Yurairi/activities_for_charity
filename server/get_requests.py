from fastapi import FastAPI, status, HTTPException
import sqlite3
from classes import MyResponse, sport_activities

app = FastAPI()


# Worker get requests
@app.get("/worker-balance/{user_id}", response_model=MyResponse)
async def get_worker_balance(user_id: int):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Retrieve the worker's balance using the user_id
        cursor.execute("SELECT balance FROM workers WHERE user_id=?", (user_id,))
        result = cursor.fetchone()

        conn.close()

        if result is None:
            response_data = {
                "status_code": 404,
                "message": "Worker not found",
                "data": {}
            }
        else:
            balance = result[0]
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"balance": balance}
            }

        return MyResponse(**response_data)
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        return MyResponse(**response_data)


# Company get requests
@app.get("/company-balance/{user_id}", response_model=MyResponse)
async def get_company_balance(user_id: int):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Retrieve the worker's balance using the user_id
        cursor.execute("SELECT balance FROM companies WHERE curator_user_id=?", (user_id,))
        result = cursor.fetchone()

        conn.close()

        if result is None:
            response_data = {
                "status_code": 404,
                "message": "Company not found",
                "data": {}
            }
        else:
            balance = result[0]
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"balance": balance}
            }

        return MyResponse(**response_data)
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        return MyResponse(**response_data)


@app.get("/companies/", response_model=MyResponse)
async def get_companies():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM companies")
    companies_data = cursor.fetchall()

    conn.close()

    if not companies_data:
        response_data = {
            "status_code": status.HTTP_404_NOT_FOUND,
            "message": "Companies not found",
            "data": {}
        }
    else:
        # Transform the list of tuples into a list of dictionaries
        companies = [
            {"id": row[0], "name": row[1], "curator_user_id": row[2], "description": row[3], "money": row[4]}
            for row in companies_data
        ]
        response_data = {
            "status_code": status.HTTP_200_OK,
            "message": "Success",
            "data": {"companies": companies}
        }

    return MyResponse(**response_data)


# Fond get requests
@app.get("/fond-balance/{user_id}", response_model=MyResponse)
async def get_fond_balance(user_id: int):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Retrieve the worker's balance using the user_id
        cursor.execute("SELECT balance FROM fonds WHERE curator_user_id=?", (user_id,))
        result = cursor.fetchone()

        conn.close()

        if result is None:
            response_data = {
                "status_code": 404,
                "message": "Fond not found",
                "data": {}
            }
        else:
            balance = result[0]
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"balance": balance}
            }

        return MyResponse(**response_data)
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        return MyResponse(**response_data)


@app.get("/fonds/", response_model=MyResponse)
async def get_fonds():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM fonds")
    fonds_data = cursor.fetchall()

    conn.close()

    if not fonds_data:
        response_data = {
            "status_code": status.HTTP_404_NOT_FOUND,
            "message": "Fonds not found",
            "data": {}
        }
    else:
        # Transform the list of tuples into a list of dictionaries
        fonds = [
            {"id": row[0], "name": row[1], "curator_user_id": row[2], "description": row[3], "money": row[4]}
            for row in fonds_data
        ]
        response_data = {
            "status_code": status.HTTP_200_OK,
            "message": "Success",
            "data": {"fonds": fonds}
        }

    return MyResponse(**response_data)


# users get request
@app.get("/users/", response_model=MyResponse)
async def get_users():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM users")
    users = cursor.fetchall()
    conn.close()

    if not users:
        response_data = {
            "status_code": status.HTTP_404_NOT_FOUND,
            "message": "Users not found",
            "data": {}  # Initialize data as an empty dictionary
        }
    else:
        # Transform the list of tuples into a dictionary
        user_data = [{"id": row[0], "username": row[1], "password": row[2], "status": row[3]} for row in users]
        response_data = {
            "status_code": status.HTTP_200_OK,
            "message": "Success",
            "data": {"users": user_data}  # Wrap user_data in a dictionary
        }

    return MyResponse(**response_data)


# offices get request
@app.get("/offices/", response_model=MyResponse)
async def get_offices():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("SELECT * FROM offices")
        offices = cursor.fetchall()
    except sqlite3.Error as e:
        raise HTTPException(status_code=500, detail=f"Database error: {str(e)}")
    finally:
        conn.close()

    if not offices:
        response_data = {
            "status_code": 404,
            "message": "No offices found",
            "data": {}
        }
    else:
        response_data = {
            "status_code": 200,
            "message": "Offices retrieved successfully",
            "data": {"offices": offices}
        }

    return MyResponse(**response_data)


@app.get("/offices/{company_id}", response_model=MyResponse)
async def get_offices_by_company_id(company_id: int):
    try:
        conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
        cursor = conn.cursor()

        # Retrieve all offices by company_id
        cursor.execute("SELECT id, company_id, name FROM offices WHERE company_id=?", (company_id,))
        offices = cursor.fetchall()

        if not offices:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "No offices found for the specified company",
                "data": {}
            }
        else:
            office_data = [{"id": row[0], "company_id": row[1], "name": row[2]} for row in offices]
            response_data = {
                "status_code": 200,
                "message": "Offices retrieved successfully",
                "data": {"offices": office_data}
            }

        conn.close()
        return MyResponse(**response_data)

    except Exception as e:
        return HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


# activities get request
@app.get("/activities/", response_model=MyResponse)
async def get_activities():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM activities")
    activities = cursor.fetchall()
    conn.close()

    if not activities:
        response_data = {
            "status_code": status.HTTP_404_NOT_FOUND,
            "message": "Activities not found",
            "data": {}  # Initialize data as an empty dictionary
        }
    else:
        # Transform the list of tuples into a list of dictionaries
        activity_data = [{"id": row[0], "name": row[1], "type": row[2], "start_date": row[3], "end_date": row[4], "place": row[5], "media": row[6],
                          "src": row[7], "description": row[8]} for row in activities]
        response_data = {
            "status_code": status.HTTP_200_OK,
            "message": "Success",
            "data": {"activities": activity_data}  # Wrap activity_data in a dictionary
        }

    return MyResponse(**response_data)


@app.get("/activities/{place}", response_model=MyResponse)
async def get_activities_by_place(place: str):
    try:
        conn = sqlite3.connect("user_db.sqlite")  # Замените на имя вашей базы данных
        cursor = conn.cursor()

        # Выполняем запрос на получение всех активностей по местоположению (place)
        cursor.execute("SELECT * FROM activities WHERE place=?", (place,))
        activities = cursor.fetchall()

        conn.close()

        if not activities:
            response_data = {
                "status_code": 404,
                "message": "Activities not found",
                "data": {}
            }
        else:
            # Преобразуем список кортежей в список словарей
            activity_data = [{"id": row[0], "name": row[1], "place": row[2]} for row in activities]
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"activities": activity_data}
            }

        return MyResponse(**response_data)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


@app.get("/user-activities/{user_id}/", response_model=MyResponse)
async def get_user_activities(user_id: int):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    cursor.execute('''SELECT ua.user_id, ua.activity_id, a.status FROM user_activities
                    ua JOIN activities a ON ua.activity_id = a.id WHERE ua.user_id=?''', (user_id,))
    user_activities_data = cursor.fetchall()

    conn.close()

    if not user_activities_data:
        response_data = {
            "status_code": status.HTTP_404_NOT_FOUND,
            "message": "User activities not found",
            "data": {}
        }
    else:
        # Transform the list of tuples into a list of dictionaries
        user_activities = [
            {"user_id": row[0], "activity_id": row[1], "status": row[2]}
            for row in user_activities_data
        ]
        response_data = {
            "status_code": status.HTTP_200_OK,
            "message": "Success",
            "data": {"user_activities": user_activities}  # Wrap user_activities in a dictionary
        }

    return MyResponse(**response_data)


# get request for sports
@app.get("/sport/", response_model=MyResponse)
async def get_sport_activities():
    try:
        # Execute a query to fetch all sport activities from the 'sports' table
        conn = sqlite3.connect("user_db.sqlite")
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM sports")

        sports = cursor.fetchall()

        # Close the database connection
        conn.close()

        # Prepare the response data
        sport_activities = [{"id": row[0], "name": row[1]} for row in sports]
        response_data = {
            "status_code": 200,
            "message": "Success",
            "data": {"sport_activities": sport_activities}
        }
        return MyResponse(**response_data)
    except Exception as e:
        # Handle any exceptions here
        # You can return an error response if something goes wrong
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        return MyResponse(**response_data)


# donations get request
@app.get("/donations/{user_id}", response_model=MyResponse)
async def get_donations_by_user_id(user_id: int):
    try:
        conn = sqlite3.connect("user_db.sqlite")  # Замените на имя вашей базы данных
        cursor = conn.cursor()

        # Выполняем запрос на получение всех донатов по user_id
        cursor.execute("SELECT * FROM donations WHERE user_id=?", (user_id,))
        donations = cursor.fetchall()

        conn.close()

        if not donations:
            response_data = {
                "status_code": 404,
                "message": "Donations not found",
                "data": {}
            }
        else:
            # Преобразуем список кортежей в список словарей
            donation_data = [{"user_id": row[0], "fond_id": row[1], "amount": row[2], "donation_date": row[3]} for row in donations]
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"donations": donation_data}
            }

        return MyResponse(**response_data)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


# cities get request
@app.get("/cities/", response_model=MyResponse)
async def get_cities():
    try:
        conn = sqlite3.connect("user_db.sqlite")  # Замените на имя вашей базы данных
        cursor = conn.cursor()

        cursor.execute("SELECT DISTINCT place FROM activities")
        cities = [row[0] for row in cursor.fetchall()]

        conn.close()

        if cities:
            response_data = {
                "status_code": 200,
                "message": "Success",
                "data": {"cities": cities}
            }
        else:
            response_data = {
                "status_code": 404,
                "message": "Cities not found",
                "data": {}
            }

        return MyResponse(**response_data)
    except Exception as e:
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        return MyResponse(**response_data)












