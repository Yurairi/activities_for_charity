from pydantic import BaseModel
from enum import Enum


class UserStatus(Enum):
    WORKER = "worker"
    COMPANY = "company"
    FOND = "fond"


class User(BaseModel):
    username: str
    password: str
    status: UserStatus


class UserAuthentication(BaseModel):
    username: str
    password: str


class Company(BaseModel):
    name: str
    curator_user_id: int
    description: str
    balance: int


class DescriptionUpdate(BaseModel):
    id: int
    new_description: str


class CompanyRegistration(BaseModel):
    username: str
    password: str
    name: str
    description: str
    balance: int


class FondRegistration(BaseModel):
    username: str
    password: str
    name: str
    description: str


class WorkerRegistration(BaseModel):
    username: str
    password: str
    office_id: int
    name: str


class Office(BaseModel):
    id: int
    company_id: int
    name: str


class Fond(BaseModel):
    name: str
    curator_user_id: int
    description: str
    balance: int


class Worker(BaseModel):
    id: int
    user_id: int
    office_id: int
    name: str
    balance: int


class BalanceUpdate(BaseModel):
    id: int
    balance: int


class DonationRequest(BaseModel):
    user_id: int
    fond_id: int
    donation_amount: int


class ActivityStatus(Enum):
    NOT_STARTED = "not started"
    FAILED = "failed"
    COMPLETED = "completed"


class Activity(BaseModel):
    name: str
    type: str
    start_date: str
    end_date: str
    place: str
    media: str
    src: str
    description: str


class UserActivity(BaseModel):
    user_id: int
    activity_id: int


class ChangeUserActivity(BaseModel):
    user_id: int
    activity_id: int
    flag: int


class Donation(BaseModel):
    user_id: int
    fond_id: int
    amount: int
    donation_date: str


class MyResponse(BaseModel):
    status_code: int
    message: str
    data: dict


sport_activities = {
    1: "Бег",
    2: "Велосипедная прогулка",
    3: "Плавание",
    4: "Ходьба",
    5: "Йога",
    6: "Пеший поход",
    7: "Аэробика",
    8: "Пилатес",
    9: "Танцы",
    10: "Ролики",
    11: "Пинг-понг",
    12: "Кардио тренировка",
    13: "Скейтбординг",
    14: "Боевые искусства (например, карате, тхэквондо)",
    15: "Скалолазание",
    16: "Занятия на турниках",
    17: "Теннис",
    18: "Бадминтон",
    19: "Силовая тренировка",
    20: "Армрестлинг"
}