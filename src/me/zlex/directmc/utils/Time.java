package me.zlex.directmc.utils;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.commons.lang.NumberUtils;
@SuppressWarnings("deprecation")
public class Time{
	private String date;
	private boolean day;
	private boolean month;
	private boolean year;
	private boolean hour;
	private boolean minutes;
	private boolean seconds;
	private String dayS;
	private String monthS;
	private String yearS;
	private String hourS;
	private String minutesS;
	private String secondsS;
	public Time(String date){
		this.date = date;
		this.day = true;
		this.month = true;
		this.year = true;
		this.hour = true;
		this.minutes = true;
		this.seconds = true;
		this.dayS = null;
		this.monthS = null;
		this.yearS = null;
		this.hourS = null;
		this.minutesS = null;
		this.secondsS = null;
	}
	public Time(boolean day, boolean month, boolean year, boolean hour, boolean minutes, boolean seconds){
		this.date = null;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
		this.dayS = null;
		this.monthS = null;
		this.yearS = null;
		this.hourS = null;
		this.minutesS = null;
		this.secondsS = null;
	}
	public Time(String day, String month, String year, String hour, String minutes, String seconds) {
		this.date = null;
		this.dayS = day;
		this.monthS = month;
		this.yearS = year;
		this.hourS = hour;
		this.minutesS = minutes;
		this.secondsS = seconds;
		this.day = false;
		this.month = false;
		this.year = false;
		this.hour = false;
		this.minutes = false;
		this.seconds = false;
	}
	public static String getStringTimeFromSecond(int seconds) {
		if (seconds < 60)
			return seconds + "s";
		if (seconds >= 60) {

			int minutes = (int) seconds / 60;
			if (minutes < 60 && seconds % 60 == 0)
				return minutes + "m";
			if (minutes < 60 && seconds % 60 != 0) {
				seconds = seconds % 60;
				return minutes + "m " + seconds + "s";
			}

			int hours = (int) minutes / 60;
			if (hours < 24 && seconds % 60 == 0 && minutes % 60 == 0)
				return hours + "h";
			if (hours < 24 && seconds % 60 != 0 && minutes % 60 == 0) {
				seconds = seconds % 60;
				return hours + "h " + seconds + "s";
			}
			if (hours < 24 && seconds % 60 == 0 && minutes % 60 != 0) {
				minutes = minutes % 60;
				return hours + "h " + minutes + "m";
			}
			if (hours < 24 && seconds % 60 != 0 && minutes % 60 != 0) {
				minutes = minutes % 60;
				seconds = seconds % 60;
				return hours + "h " + minutes + "m " + seconds + "s";
			}
			if (hours >= 24) {
				int day = (int) hours / 24;
				if (hours % 24 != 0)
					hours = hours % 24;
				if (seconds % 60 != 0)
					seconds = seconds % 60;
				if (minutes % 60 != 0)
					minutes = minutes % 60;
				if (hours % 24 == 0 && seconds % 60 == 0 && minutes % 60 == 0)
					return day + "d";
				if (hours % 24 != 0 && seconds % 60 == 0 && minutes % 60 == 0)
					return day + "d " + hours + "h";
				if (hours % 24 == 0 && seconds % 60 != 0 && minutes % 60 == 0)
					return day + "d " + seconds + "s";
				if (hours % 24 != 0 && seconds % 60 != 0 && minutes % 60 == 0)
					return day + "d " + hours + "h " + seconds + "s";
				if (hours % 24 != 0 && seconds % 60 != 0 && minutes % 60 != 0)
					return day + "d " + hours + "h " + minutes + "m " + seconds + "s";
				if (hours % 24 == 0 && seconds % 60 != 0 && minutes % 60 != 0)
					return day + "d " + minutes + "m " + seconds + "s";
				if (hours % 24 == 0 && seconds % 60 == 0 && minutes % 60 != 0)
					return day + "d " + minutes + "m";
			}
		}
		return seconds + "s";
	}
	public boolean isPastOrPresent() {
		Time today = new Time(getToDay());
		if (this.getYear() < today.getYear()) {
			return true;
		} else if (this.getYear() == today.getYear()) {
			if (this.getMonth() < today.getMonth()) {
				return true;
			} else if (this.getMonth() == today.getMonth()) {
				if (this.getDay() < today.getDay()) {
					return true;
				} else if (this.getDay() == today.getDay()) {
					if (this.getHour() < today.getHour()) {
						return true;
					} else if (this.getHour() == today.getHour()) {
						if (this.getMinutes() < today.getMinutes()) {
							return true;
						} else if (this.getMinutes() == today.getMinutes()) {
							if (this.getSeconds() <= today.getSeconds()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public boolean isDay() {
		return this.date.contains("d");
	}
	public boolean isMonth() {
		return this.date.contains("M");
	}
	public boolean isYear() {
		return this.date.contains("y");
	}
	public boolean isHour() {
		return this.date.contains("h");
	}
	public boolean isMinute() {
		return this.date.contains("m");
	}
	public boolean isSecond() {
		return this.date.contains("s");
	}
	public int translateDay() {
		return Integer.parseInt(this.dayS.substring(0, this.dayS.length() - 1));
	}
	public int translateMonth() {
		return Integer.parseInt(this.monthS.substring(0, this.monthS.length() - 1));
	}
	public int translateYear() {
		return Integer.parseInt(this.yearS.substring(0, this.yearS.length() - 1));
	}
	public int translateHour() {
		return Integer.parseInt(this.hourS.substring(0, this.hourS.length() - 1));
	}
	public int translateMinutes() {
		return Integer.parseInt(this.minutesS.substring(0, this.minutesS.length() - 1));
	}
	public int translateSeconds() {
		return Integer.parseInt(this.secondsS.substring(0, this.secondsS.length() - 1));
	}
	public String getDate() {
		String date = "";
		if (this.day) {
			date = LocalDate.now().getDayOfMonth() + "";
		}
		if (this.month) {
			if (!date.isEmpty()) {
				date += date + "/" + LocalDate.now().getMonthValue();
			} else {
				date = "" + LocalDate.now().getMonthValue();
			}
		}
		if (this.year) {
			if (!date.isEmpty()) {
				date += date + "/" + LocalDate.now().getYear();
			} else {
				date = "" + LocalDate.now().getYear();
			}
		}
		if (this.hour) {
			if (!date.isEmpty()) {
				date += date + " " + LocalTime.now().getHour();
			} else {
				date = "" + LocalTime.now().getHour();
			}
		}
		if (this.minutes) {
			if (!date.isEmpty()) {
				if (this.hour)
					date += date + ":" + LocalTime.now().getMinute();
				else
					date += date + " " + LocalTime.now().getMinute();
			} else {
				date = "" + LocalTime.now().getMinute();
			}
		}
		if (this.seconds) {
			if (!date.isEmpty()) {
				if (this.minutes)
					date += date + ":" + LocalTime.now().getSecond();
				else
					date += date + " " + LocalTime.now().getSecond();
			} else {
				date = "" + LocalTime.now().getSecond();
			}
		}
		this.date = date;
		return date;
	}
	public Long getDateInSeconds() {
		int s = getSeconds();
		Long m = (long) (getMinutes()*60);
		Long h = (long) (getHour()*60*60);
		Long d = (long) (getDay()*24*60*60);
		Long M = (long) getMonth();
		switch (getMonth()) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			M=M*31*24*60*60;
			break;
		case 2:
			M=M*28*24*60*60;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			M=M*30*24*60*60;
			break;
		default:
			M=M*30*24*60*60;
			break;
		}	
		Long y = (long) (getYear()*12*30*24*60*60);	
		return y+M+d+h+m+s;
	}
	public int getDay() {
		return Integer.parseInt(this.date.split(" ")[0].split("/")[0]);
	}
	public int getMonth() {
		return Integer.parseInt(this.date.split(" ")[0].split("/")[1]);
	}
	public int getYear() {
		return Integer.parseInt(this.date.split(" ")[0].split("/")[2]);
	}
	public int getHour() {
		return Integer.parseInt(this.date.split(" ")[1].split(":")[0]);
	}
	public int getMinutes() {
		return Integer.parseInt(this.date.split(" ")[1].split(":")[1]);
	}
	public int getSeconds() {
		return Integer.parseInt(this.date.split(" ")[1].split(":")[2]);
	}
	public String addTime(int days, int months, int years, int hours, int minutes, int seconds) {
		int s = getSeconds() + seconds;
		int m = getMinutes() + minutes;
		int h = getHour() + hours;
		int d = getDay() + days;
		int mese = getMonth() + months;
		int y = getYear() + years;
		if (s >= 60) {
			while (s >= 60) {
				int calcolos = s / 60;
				if (s % 60 > 0) {
					s = s % 60;
				} else {
					s = s - 60;
				}
				m = m + calcolos;
			}
		}
		if (m >= 60) {
			while (m >= 60) {
				int calcoloh = m / 60;
				if (m % 60 > 0) {
					m = m % 60;
				} else {
					m = m - 60;
				}
				h = h + calcoloh;
			}
		}
		if (h >= 24) {
			while (h >= 24) {
				int calcolod = h / 24;
				if (h % 24 > 0) {
					h = h % 24;
				} else {
					h = h - 24;
				}
				d = d + calcolod;
			}
		}
		int newmese = mese;
		do {
			newmese = mese;
			switch (mese) {
			case 1:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 2:
				while (d > 28) {
					if (d > 28) {
						d = d - 28;
						mese = mese + 1;
					}
				}
				break;
			case 3:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 4:
				while (d > 30) {
					if (d > 30) {
						d = d - 30;
						mese = mese + 1;
					}
				}
				break;
			case 5:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 6:
				while (d > 30) {
					if (d > 30) {
						d = d - 30;
						mese = mese + 1;
					}
				}
				break;
			case 7:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 8:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 9:
				while (d > 30) {
					if (d > 30) {
						d = d - 30;
						mese = mese + 1;
					}
				}
				break;
			case 10:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			case 11:
				while (d > 30) {
					if (d > 30) {
						d = d - 30;
						mese = mese + 1;
					}
				}
				break;
			case 12:
				while (d > 31) {
					if (d > 31) {
						d = d - 31;
						mese = mese + 1;
					}
				}
				break;
			default:
				mese = mese + 0;
				break;
			}
		} while (mese != newmese);
		if (mese > 12) {
			while (mese > 12) {
				int calcoloy = mese / 12;
				if (mese % 12 > 0) {
					mese = mese % 12;
				} else {
					mese = mese - 12;
				}
				y = y + calcoloy;
			}
		}
		String mm = "", hh = "", ss = "";
		if (m < 10) {
			mm = "0" + m;
		} else {
			mm = "" + m;
		}
		if (h < 10) {
			hh = "0" + h;
		} else {
			hh = "" + h;
		}
		if (s < 10) {
			ss = "0" + s;
		} else {
			ss = "" + s;
		}
		return d + "/" + mese + "/" + y + " " + hh + ":" + mm + ":" + ss;
	}
	public static boolean isTimeFormat(String format) {
		if (NumberUtils.isNumber(format.replaceAll("s", "")))
			return true;
		if (NumberUtils.isNumber(format.replaceAll("m", "")))
			return true;
		if (NumberUtils.isNumber(format.replaceAll("h", "")))
			return true;
		if (NumberUtils.isNumber(format.replaceAll("d", "")))
			return true;
		if (NumberUtils.isNumber(format.replaceAll("y", "")))
			return true;
		return false;
	}
	public static String getToDay() {
		return LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear()
				+ " " + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
				+ LocalTime.now().getSecond();
	}
}