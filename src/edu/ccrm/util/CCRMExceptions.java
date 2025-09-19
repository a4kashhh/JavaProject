package edu.ccrm.util;

public class CCRMExceptions {
    public static class DuplicateEnrollmentException extends Exception {
        public DuplicateEnrollmentException(String msg){ super(msg); }
    }
    public static class MaxCreditLimitExceededException extends Exception {
        public MaxCreditLimitExceededException(String msg){ super(msg); }
    }
}
