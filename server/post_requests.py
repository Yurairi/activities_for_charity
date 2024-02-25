from fastapi import FastAPI, HTTPException, status, Body
import sqlite3
from classes import User, Company, Fond, Office, Worker, Activity, UserActivity, Donation, MyResponse,\
                    CompanyRegistration, WorkerRegistration, FondRegistration, UserAuthentication,\
                    BalanceUpdate, DonationRequest, DescriptionUpdate, ChangeUserActivity

app = FastAPI()


# worker responses
@app.post("/update-worker-balance/", response_model=MyResponse)
async def update_worker_balance(balance_update: BalanceUpdate):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Check if the worker exists
        cursor.execute("SELECT id FROM workers WHERE user_id=?", (balance_update.id,))
        result = cursor.fetchone()

        if result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Worker not found",
                "data": {}
            }
        else:
            # Update the worker's balance
            cursor.execute("UPDATE workers SET balance = ? WHERE user_id=?", (balance_update.balance, balance_update.id))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": 200,
                "message": "Worker balance updated successfully",
                "data": {"user_id": balance_update.id, "balance": balance_update.balance}
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


@app.post("/register-worker/", response_model=MyResponse)
async def register_worker(worker_registration: WorkerRegistration):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("SELECT username FROM users WHERE username=?", (worker_registration.username,))
        existing_user = cursor.fetchone()

        if existing_user:
            conn.close()
            response_data = {
                "status_code": status.HTTP_400_BAD_REQUEST,
                "message": "Username already exists",
                "data": {}
            }
        else:
            # Insert the user into the 'users' table
            cursor.execute("INSERT INTO users (username, password, status) VALUES (?, ?, ?)",
                           (worker_registration.username, worker_registration.password, "worker"))
            user_id = cursor.lastrowid

            # Insert the user into the 'workers' table
            cursor.execute("INSERT INTO workers (user_id, office_id, name, balance) VALUES (?, ?, ?, 0)",
                           (user_id, worker_registration.office_id, worker_registration.name))

            conn.commit()
            conn.close()

            response_data = {
                "status_code": status.HTTP_201_CREATED,
                "message": "User registered successfully",
                "data": {"user_id": user_id, "status": "worker"}
            }
    except sqlite3.IntegrityError:
        conn.close()
        response_data = {
            "status_code": status.HTTP_400_BAD_REQUEST,
            "message": "Error occurred during registration",
            "data": {}
        }
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": status.HTTP_500_INTERNAL_SERVER_ERROR,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }

    return MyResponse(**response_data)


# company requests
@app.post("/update-company-balance/", response_model=MyResponse)
async def update_company_balance(balance_update: BalanceUpdate):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Check if the worker exists
        cursor.execute("SELECT id FROM companies WHERE curator_user_id=?", (balance_update.id,))
        result = cursor.fetchone()

        if result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Company not found",
                "data": {}
            }
        else:
            # Update the worker's balance
            cursor.execute("UPDATE companies SET balance = ? WHERE curator_user_id=?", (balance_update.balance, balance_update.id))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": 200,
                "message": "Company balance updated successfully",
                "data": {"user_id": balance_update.id, "balance": balance_update.balance}
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


@app.post("/register-company/", response_model=MyResponse)
async def register_company(company_registration: CompanyRegistration):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("SELECT username FROM users WHERE username=?", (company_registration.username,))
        existing_user = cursor.fetchone()

        if existing_user:
            conn.close()
            response_data = {
                "status_code": status.HTTP_400_BAD_REQUEST,
                "message": "Username already exists",
                "data": {}
            }
        else:
            # Insert the user into the 'users' table
            cursor.execute("INSERT INTO users (username, password, status) VALUES (?, ?, ?)",
                           (company_registration.username, company_registration.password, "company"))
            user_id = cursor.lastrowid

            # Insert the user into the 'workers' table
            cursor.execute("INSERT INTO companies (name, curator_user_id, description, balance) VALUES (?, ?, ?, ?)",
                           (company_registration.name, user_id, company_registration.description, company_registration.balance))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": status.HTTP_201_CREATED,
                "message": "User registered successfully",
                "data": {"user_id": user_id, "status": "company"}
            }
    except sqlite3.IntegrityError:
        conn.close()
        response_data = {
            "status_code": status.HTTP_400_BAD_REQUEST,
            "message": "Error occurred during registration",
            "data": {}
        }
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": status.HTTP_500_INTERNAL_SERVER_ERROR,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }

    return MyResponse(**response_data)


@app.post("/update-company-description/", response_model=MyResponse)
async def update_company_description(company_data: DescriptionUpdate = Body(...)):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Check if the company exists
        cursor.execute("SELECT id FROM companies WHERE id=?", (company_data.id,))
        company_result = cursor.fetchone()

        if company_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Company not found",
                "data": {}
            }
            raise HTTPException(status_code=404, detail="Company not found")

        # Update the company's description
        cursor.execute("UPDATE companies SET description = ? WHERE id=?", (company_data.new_description, company_data.id))
        conn.commit()
        conn.close()

        response_data = {
            "status_code": 200,
            "message": "Company description updated successfully",
            "data": {}
        }

        return MyResponse(**response_data)

    except Exception as e:
        conn.close()
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


# fond requests
@app.post("/update-fond-balance/", response_model=MyResponse)
async def update_fond_balance(balance_update: BalanceUpdate):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Check if the worker exists
        cursor.execute("SELECT id FROM fonds WHERE curator_user_id=?", (balance_update.id,))
        result = cursor.fetchone()

        if result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Fond not found",
                "data": {}
            }
        else:
            # Update the worker's balance
            cursor.execute("UPDATE fonds SET balance = ? WHERE curator_user_id=?", (balance_update.balance, balance_update.id))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": 200,
                "message": "Fond balance updated successfully",
                "data": {"user_id": balance_update.id, "balance": balance_update.balance}
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


@app.post("/register-fond/", response_model=MyResponse)
async def register_fond(fond_registration: FondRegistration):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("SELECT username FROM users WHERE username=?", (fond_registration.username,))
        existing_user = cursor.fetchone()

        if existing_user:
            conn.close()
            response_data = {
                "status_code": status.HTTP_400_BAD_REQUEST,
                "message": "Username already exists",
                "data": {}
            }
        else:
            # Insert the user into the 'users' table
            cursor.execute("INSERT INTO users (username, password, status) VALUES (?, ?, ?)",
                           (fond_registration.username, fond_registration.password, "fond"))
            user_id = cursor.lastrowid

            cursor.execute("INSERT INTO fonds (name, curator_user_id, description, balance) VALUES (?, ?, ?, 0)",
                           (fond_registration.name, user_id, fond_registration.description))

            conn.commit()
            conn.close()

            response_data = {
                "status_code": status.HTTP_201_CREATED,
                "message": "User registered successfully",
                "data": {"user_id": user_id, "status": "fond"}
            }
    except sqlite3.IntegrityError:
        conn.close()
        response_data = {
            "status_code": status.HTTP_400_BAD_REQUEST,
            "message": "Error occurred during registration",
            "data": {}
        }
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": status.HTTP_500_INTERNAL_SERVER_ERROR,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }

    return MyResponse(**response_data)


@app.post("/update-fond-description/", response_model=MyResponse)
async def update_fond_description(fond_data: DescriptionUpdate = Body(...)):
    conn = sqlite3.connect("user_db.sqlite")  # Replace with your actual database file name
    cursor = conn.cursor()

    try:
        # Check if the company exists
        cursor.execute("SELECT id FROM fonds WHERE id=?", (fond_data.id,))
        company_result = cursor.fetchone()

        if company_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Company not found",
                "data": {}
            }
            raise HTTPException(status_code=404, detail="Company not found")

        # Update the company's description
        cursor.execute("UPDATE fonds SET description = ? WHERE id=?", (fond_data.new_description, fond_data.id))
        conn.commit()
        conn.close()

        response_data = {
            "status_code": 200,
            "message": "Fond description updated successfully",
            "data": {}
        }

        return MyResponse(**response_data)

    except Exception as e:
        conn.close()
        response_data = {
            "status_code": 500,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


# donation requests
@app.post("/donate/", response_model=MyResponse)
async def make_donation(donation_request: DonationRequest = Body(...)):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        # Check if the worker (user) exists
        cursor.execute("SELECT balance, office_id FROM workers WHERE id=?", (donation_request.user_id,))
        worker_result = cursor.fetchone()

        if worker_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Worker not found",
                "data": {}
            }
            return MyResponse(**response_data)

        worker_balance, office_id = worker_result

        # Retrieve the company_id from the Office based on office_id
        cursor.execute("SELECT company_id FROM offices WHERE id=?", (office_id,))
        office_result = cursor.fetchone()

        if office_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Office not found",
                "data": {}
            }
            return MyResponse(**response_data)

        company_id = office_result[0]

        # Check if the worker has enough balance
        if worker_balance < donation_request.donation_amount:
            conn.close()
            response_data = {
                "status_code": 400,
                "message": "Worker doesn't have enough balance",
                "data": {}
            }
            return MyResponse(**response_data)

        # Check if the fond (funds) exists
        cursor.execute("SELECT id FROM fonds WHERE id=?", (donation_request.fond_id,))
        fond_result = cursor.fetchone()

        if fond_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "Fond not found",
                "data": {}
            }
            return MyResponse(**response_data)

        # Subtract the donation amount from the worker's balance
        cursor.execute("UPDATE workers SET balance = balance - ? WHERE id=?", (donation_request.donation_amount, donation_request.user_id))

        # Subtract the donation amount from the company's balance
        cursor.execute("UPDATE companies SET balance = balance - ? WHERE id=?", (donation_request.donation_amount, company_id))

        # Add the donation amount to the fond's balance
        cursor.execute("UPDATE fonds SET balance = balance + ? WHERE id=?", (donation_request.donation_amount, donation_request.fond_id))

        # Add the donation entry to the "donations" table with the current date
        cursor.execute("INSERT INTO donations (user_id, fond_id, amount, donation_date) VALUES (?, ?, ?, DATE('now'))",
                       (donation_request.user_id, donation_request.fond_id, donation_request.donation_amount))

        # Commit the changes to the database
        conn.commit()
        conn.close()

        response_data = {
            "status_code": 200,
            "message": "Donation successful",
            "data": {}
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


# autorization requests
@app.post("/login/", response_model=MyResponse)
async def login(user_authentication: UserAuthentication):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        # Execute the authentication query
        cursor.execute("SELECT id, status FROM users WHERE username=? AND password=?",
                       (user_authentication.username, user_authentication.password))
        result = cursor.fetchone()

        conn.close()

        if result:
            user_id, user_status = result
            response_data = {
                "status_code": status.HTTP_200_OK,
                "message": "Login successful",
                "data": {"id": user_id, "status": user_status}
            }
        else:
            response_data = {
                "status_code": status.HTTP_401_UNAUTHORIZED,
                "message": "Invalid credentials",
                "data": {}
            }
    except Exception as e:
        conn.close()
        response_data = {
            "status_code": status.HTTP_500_INTERNAL_SERVER_ERROR,
            "message": f"Internal Server Error: {str(e)}",
            "data": {}
        }

    return MyResponse(**response_data)


#offices requests
@app.post("/offices/", response_model=MyResponse)
async def create_office(office: Office):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("INSERT INTO offices (company_id, name) VALUES (?, ?)",
                       (office.company_id, office.name))
        office_id = cursor.lastrowid
        conn.commit()
        conn.close()

        response_data = {
            "status_code": 200,
            "message": "Office created successfully",
            "data": {"office_id": office_id}
        }
    except Exception as e:
        conn.close()
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=f"Error occurred: {str(e)}")

    return MyResponse(**response_data)


#activities requests
@app.post("/activities/", response_model=Activity)
async def create_activity(activity: Activity):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        cursor.execute("INSERT INTO activities (name, type, start_date, end_date, place, media, src, description) "
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                       (activity.name, activity.type, activity.start_date, activity.end_date, activity.place, activity.media, activity.src, activity.description))
        activity_id = cursor.lastrowid
        conn.commit()
        conn.close()
        return {"id": activity_id, **activity.model_dump()}
    except sqlite3.IntegrityError:
        conn.close()
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Error occurred while creating activity")


@app.post("/user-activities/", response_model=MyResponse)
async def add_user_activity(user_activity: UserActivity):
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    try:
        # Получить дату активности и статус из таблицы activities по activity_id
        cursor.execute("SELECT start_date FROM activities WHERE id=?", (user_activity.activity_id,))
        activity_date = cursor.fetchone()

        if activity_date:
            activity_date = activity_date[0]  # Получить дату из результата запроса

            # Вставить запись в таблицу user_activities с полученной датой активности
            cursor.execute(
                "INSERT INTO user_activities (user_id, activity_id, visit_date, status) VALUES (?, ?, ?, ?)",
                (user_activity.user_id, user_activity.activity_id, activity_date, "not started"))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": 200,
                "message": "User Activity added successfully",
                "data": user_activity.model_dump()
            }

            return MyResponse(**response_data)
        else:
            conn.close()
            raise HTTPException(status_code=404, detail="Activity not found")
    except sqlite3.IntegrityError:
        conn.close()
        raise HTTPException(status_code=400, detail="Error occurred while adding user activity")


@app.post("/update-activity-status/", response_model=MyResponse)
async def update_activity_status(user_activity: ChangeUserActivity):
    try:
        conn = sqlite3.connect("user_db.sqlite")  # Замените на имя вашей базы данных
        cursor = conn.cursor()

        # Проверяем существование записи о пользовательской активности
        cursor.execute("SELECT id FROM user_activities WHERE user_id=? AND activity_id=?",
                       (user_activity.user_id, user_activity.activity_id))
        activity_result = cursor.fetchone()

        if activity_result is None:
            conn.close()
            response_data = {
                "status_code": 404,
                "message": "User Activity not found",
                "data": {}
            }
        else:
            # Обновляем статус активности на основе флага
            status = "failed" if user_activity.flag == 1 else "completed"
            cursor.execute("UPDATE user_activities SET status=? WHERE id=?", (status, activity_result[0]))
            conn.commit()
            conn.close()

            response_data = {
                "status_code": 200,
                "message": "User Activity status updated successfully",
                "data": {"user_id": user_activity.user_id, "activity_id": user_activity.activity_id, "new_status": status}
            }

        return MyResponse(**response_data)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")