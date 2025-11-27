from locust import User, task


class DummyUser(User):
    @task
    def noop(self):
        # This user does nothing; Java locust4j workers generate the real load.
        pass
