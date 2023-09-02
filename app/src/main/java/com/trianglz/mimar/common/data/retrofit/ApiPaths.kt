/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 4:51 PM
 *
 */

package com.trianglz.mimar.common.data.retrofit

class ApiPaths {
    companion object {
        const val CUSTOMERS = "customers"
        const val ID = "id"
        const val NOTIFICATIONS = "customer_app_notifications"
        const val CUSTOMER_ADDRESSES ="customer_addresses"
        const val SET_NOTIFICATION_SEEN = "$NOTIFICATIONS/{id}"
        const val SESSIONS = "sessions"

        const val COUNTRIES= "countries"

        //region AWS
        const val GET_CREDENTIALS = "aws/credentials"
        const val ForgotPassword = "$CUSTOMERS/forget_password"
        const val ChangePassword = "$CUSTOMERS/change_password"
        const val UPDATE_PROFILE ="$CUSTOMERS/update_profile"
        const val VERIFY_PHONE ="$CUSTOMERS/verify_phone_number"
        const val RESEND_CODE ="$CUSTOMERS/resend_phone_verificaiton"
        const val SOCIAL_LOGIN = "oauth/login"

        const val Customer_Addresses ="customer_addresses"
        const val SET_DEFAULT_ADDRESS ="$Customer_Addresses/{id}/set_default"
        const val DELETE_ADDRESS = "$Customer_Addresses/{id}"

        const val UPDATE_ADDRESS="$CUSTOMER_ADDRESSES/{id}"
        const val FETCH_DATA_MAP = "$COUNTRIES/data"
        //endregion

        const val BRANCHES = "branches"
        const val CATEGORIES = "categories"
        const val TOGGLE_FAVORITE = "$BRANCHES/{$ID}/toggle_favorite"
        const val EMPLOYEES ="employees"
        const val GET_BRANCH_DETAILS = "$BRANCHES/{$ID}"

        const val SPECIALITIES = "specialties"
        const val BRANCH_SERVICES="branch_services"
        const val CART ="carts"
        const val UPDATE_CART ="$CART/update_cart"
        const val MY_CART ="$CART/my_cart"
        const val CLEAR_CART ="$CART/clear"
        const val GET_OTHER_BRANCHES ="$BRANCHES/{$ID}/other_branches"
        const val FREE_SLOTS ="$BRANCHES/{$ID}/free_slots"
        const val CART_BRANCH_SERVICES ="cart_branch_services"
        const val BRANCH_REVIEWS ="branch_reviews"
        const val CART_BRANCH_SERVICES_OPERATION ="$CART_BRANCH_SERVICES/{$ID}"
        const val LOOKUPS ="lookups"

        const val FAVORITE_BRANCHES = "$BRANCHES/favorites"

        const val APPOINTMENTS = "appointments"
        const val NEXT_APPOINTMENT = "$APPOINTMENTS/next_appointment"
        const val APPOINTMENT_Details = "$APPOINTMENTS/{id}"

        const val APPOINTMENT_BRANCH_SERVICE = "appointment_branch_services/{id}"
        const val APPOINTMENT_PROBLEM_REASONS = "customer_appointment_problem_reasons"

        const val BRANCH_SPECIALITIES = "branch_specialties"
        private const val PAYMENTS = "payments"
        const val VERIFY_PAYMENT = "$PAYMENTS/verify_payment"
        const val REQUEST_CHECKOUT_ID = "$PAYMENTS/request_checkout_id"
        const val DELETE_ACCOUNT = "$CUSTOMERS/delete_account"
        const val CANCELLATION_POLICY = "confirmation_messages/cancellation_policy"
        const val EMPLOYEES_DETAILS ="$EMPLOYEES/{id}"


    }
}

class ApiQueries {
    companion object {
        const val PAGE = "page"
        const val ITEMS = "items"
        const val NAME = "name"
        const val TITLE = "title"
        const val USER_ID = "user_id"
        const val FILTER_OPTION_ID = "filter_option_id"
        const val IS_IN_MY_AGENDA = "is_in_my_agenda"
        const val DATES = "dates"
        const val LOCALE ="locale"
        const val LAT= "lat"
        const val LNG = "lng"
        const val CATEGORY_ID = "category_id"
        const val LIST = "list"
        const val SERVICED_GENDER = "serviced_gender"
        const val SPECIALTY_IDS = "specialty_ids"
        const val OFFERED_LOCATION = "offered_location"
        const val RATING = "rating"
        const val EMPLOYEE_ID = "employee_id"
        const val SPECIALITY_ID = "specialty_id"
        const val BRANCH_ID = "branch_id"
        const val CART_SERVICE_ID = "cart_branch_service_id"
        const val DATE = "date"
        const val GENDERS = "genders"
        const val OFFERED_LOCATIONS = "offered_locations"
        const val SPECIALITIES = "specialties"
        const val BRANCH_SPECIALITY_ID = "branch_specialty_id"
        const val AVAILABLE_DATE = "available_date"
        const val AVAILABLE_TIME = "available_time"
        const val STATUS = "status"

    }

    class Flags {
        companion object {
            const val FLAG_SUPPORTED = "flag_supported"
        }
    }
}
