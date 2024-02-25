import sqlite3
from util_functions import fill_sports_table


def create_users_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE NOT NULL,
            password TEXT NOT NULL,
            status TEXT NOT NULL
        )
    ''')


def create_companies_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS companies (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            curator_user_id INTEGER NOT NULL,
            name TEXT NOT NULL,
            description TEXT NO NULL,
            balance INTEGER NOT NULL,
            FOREIGN KEY (curator_user_id) REFERENCES users (id)
        )
    ''')


def create_offices_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS offices (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            company_id INTEGER NOT NULL,
            name TEXT NOT NULL,
            FOREIGN KEY (company_id) REFERENCES companies (id)
        )
    ''')


def create_fonds_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS fonds (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            curator_user_id INTEGER NOT NULL,
            name TEXT NOT NULL,
            description TEXT NO NULL,
            balance INTEGER NOT NULL,
            FOREIGN KEY (curator_user_id) REFERENCES users (id)
        )
    ''')


def create_workers_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS workers (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            office_id INTEGER NOT NULL,
            name TEXT NOT NULL,
            balance INTEGER NOT NULL,
            FOREIGN KEY (user_id) REFERENCES users (id)
            FOREIGN KEY (office_id) REFERENCES offices (id)
        )
    ''')


def create_activities_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS activities (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            type TEXT NOT NULL,
            name TEXT NOT NULL,
            start_date TEXT NOT NULL,
            end_date TEXT NOT NULL,
            place TEXT NOT NULL,
            media TEXT NOT NULL,
            src TEXT NOT NULL,
            description TEXT NOT NULL
        )
    ''')


def create_user_activities_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS user_activities (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            activity_id INTEGER NOT NULL,
            visit_date DATE,
            status TEXT NOT NULL,
            FOREIGN KEY (user_id) REFERENCES users (id),
            FOREIGN KEY (activity_id) REFERENCES activities (id)
        )
    ''')


def create_donations_table(cursor):
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS donations (
            user_id INTEGER NOT NULL,
            fond_id INTEGER NOT NULL,
            amount INTEGER NOT NULL,
            donation_date DATE,
            FOREIGN KEY (user_id) REFERENCES users (id),
            FOREIGN KEY (fond_id) REFERENCES fonds (id)
        )
    ''')


def create_sports_table(cursor):
    cursor.execute('''
            CREATE TABLE IF NOT EXISTS sports (
                id INTEGER NOT NULL,
                name TEXT NOT NULL
            )
        ''')


def create_tables():
    conn = sqlite3.connect("user_db.sqlite")
    cursor = conn.cursor()

    create_users_table(cursor)
    create_companies_table(cursor)
    create_offices_table(cursor)
    create_fonds_table(cursor)
    create_workers_table(cursor)
    create_activities_table(cursor)
    create_user_activities_table(cursor)
    create_donations_table(cursor)
    create_sports_table(cursor)
    fill_sports_table(cursor)

    conn.commit()
    conn.close()
