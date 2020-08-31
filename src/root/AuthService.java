package root;

import java.util.Objects;
import java.util.Set;

public interface AuthService {
    Record findRecord(String login, String password);
    Set<Record> getRecords();
    void setRecord(String name,String login,String password);

    class Record {
        private long id;
        private String name;
        private String login;
        private String password;
        private String avatar;

        public Record(long id, String name, String login, String password, String avatar) {
            this.id = id;
            this.name = name;
            this.login = login;
            this.password = password;
            this.avatar = avatar;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getAvatar() {
            return avatar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Record record = (Record) o;
            return id == record.id &&
                    name.equals(record.name) &&
                    login.equals(record.login) &&
                    password.equals(record.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, login, password);
        }
    }
}
