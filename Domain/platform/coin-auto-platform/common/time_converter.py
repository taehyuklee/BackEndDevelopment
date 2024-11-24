from datetime import datetime
import pytz


def convert_utc_to_kst(utc_time_str: str) -> str:
    """
    Convert UTC time string to KST time string.

    :param utc_time_str: ISO8601 formatted UTC time string (e.g., "2024-11-23T08:15:00Z")
    :return: ISO8601 formatted KST time string (e.g., "2024-11-23T17:15:00+09:00")
    """
    # Define UTC and KST time zones
    utc_tz = pytz.utc
    kst_tz = pytz.timezone('Asia/Seoul')

    # Parse the input UTC time string into a datetime object
    utc_dt = datetime.fromisoformat(utc_time_str.replace("Z", "+00:00"))

    # Convert UTC time to KST time
    kst_dt = utc_dt.astimezone(kst_tz)

    # Return the KST time in ISO8601 format
    return kst_dt.isoformat()


# Example usage
utc_time = "2024-11-23T08:15:00Z"
kst_time = convert_utc_to_kst(utc_time)
print(kst_time)  # 출력: 2024-11-23T17:15:00+09:00

__all__ = ['convert_utc_to_kst']