package com.example.sergio.iter1demo;

public enum Auspiciadores {
    ONE {
        public String getDescription() {
            return "PLAZA SAN MIGUEL";
        }
        public int getNameVideo() {
            return R.raw.video_plaza_san_miguel;
        }
    },
    TWO {
        public String getDescription() {
            return "COCA COLA";
        }
        public int getNameVideo() {
            return R.raw.video_coca_cola;
        }
        },
    THREE {
        public String getDescription() {
            return "RIPLEY";
        }
        public int getNameVideo() {
            return R.raw.video_ripley;
        }
    },
    FOUR {
        public String getDescription() {
            return "WONG";
        }
        public int getNameVideo() {
            return R.raw.video_wong;
        }
    };

    public abstract String getDescription();
    public abstract int getNameVideo();
}
