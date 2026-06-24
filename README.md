# Student Information System on Kubernetes

## Project Overview

This project demonstrates the deployment of a multi-tier application on Google Kubernetes Engine (GKE).

The solution consists of:

* Spring Boot REST API (Service Tier)
* MySQL Database (Database Tier)
* Kubernetes Deployments, Services, Ingress, ConfigMaps, Secrets, PVC, and HPA

The API fetches student information from the MySQL database and exposes it through a REST endpoint.

---

## Repository Information

### Source Code Repository

GitHub Repository:

https://github.com/DevAnuragGarg/KubernetesDemo

---

## Docker Images

### Application Image

Docker Hub Repository:

https://hub.docker.com/repository/docker/devanurag/student-info

Image Used:

devanurag/student-info:v5

---

## Service API URL

API Endpoint:

http://8.233.153.189/students

Example Response:

```json
[
  {
    "id": 1,
    "name": "Anurag Garg",
    "age": 35,
    "gender": "Male",
    "score": 95
  }
]
```

---

## Architecture

Internet
→ GKE Load Balancer
→ Ingress
→ student-api-service (ClusterIP)
→ Spring Boot Pods (4 Replicas)
→ mysql-service (ClusterIP)
→ MySQL Pod
→ Persistent Volume Claim

---

## Kubernetes Resources

### Namespace

student-namespace

### Database Tier

* MySQL 8.0
* Deployment (1 Replica)
* ClusterIP Service
* PersistentVolumeClaim (5Gi)
* Secret for database credentials

### Service/API Tier

* Spring Boot REST API
* Deployment (4 Replicas)
* ClusterIP Service
* ConfigMap for database configuration
* Secret for database password
* Ingress for external access
* Horizontal Pod Autoscaler (HPA)

---

## Student Entity

The application manages student information using the following entity:

```java
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String gender;
    private Integer score;
}
```

---

## Configuration Management

### ConfigMap

The following database configuration values are externalized using ConfigMap:

* DB_HOST
* DB_PORT
* DB_NAME
* DB_USERNAME

### Secret

The following sensitive values are stored in Kubernetes Secrets:

* DB_ROOT_PASSWORD
* DB_PASSWORD

---

## Persistent Storage

MySQL uses a PersistentVolumeClaim (PVC) for persistent storage.

Benefits:

* Data survives pod recreation.
* Data survives deployment updates.
* Demonstrates Kubernetes persistent storage capabilities.
* Meets assignment persistence requirements.

---

## Deployment Strategy

Rolling Update strategy is configured for the API deployment.

```yaml
strategy:
  type: RollingUpdate
  rollingUpdate:
    maxUnavailable: 1
    maxSurge: 0
```

Benefits:

* Zero downtime deployments.
* Controlled pod replacement.
* Easy rollback support through ReplicaSets.
* Improved application availability.

---

## Horizontal Pod Autoscaler (HPA)

The API deployment uses HPA based on CPU utilization.

Configuration:

* Minimum Replicas: 4
* Maximum Replicas: 8
* CPU Utilization Target: 70%

Benefits:

* Automatic scaling based on workload.
* Efficient resource utilization.
* Reduced operational cost.
* Improved application responsiveness.

---

## Self-Healing Demonstration

### API Tier

* Delete an API pod.
* Deployment automatically recreates the pod.
* Desired replica count remains 4.

### Database Tier

* Delete MySQL pod.
* Deployment automatically recreates the pod.
* Database remains available after recovery.

---

## Persistence Demonstration

MySQL data is stored on a Persistent Volume Claim.

Demonstration:

1. Insert student records.
2. Delete MySQL pod.
3. New MySQL pod is automatically created.
4. API continues to return previously stored student records.

This demonstrates that database data survives pod recreation.

---

## FinOps Considerations

### Resource Requests and Limits

```yaml
resources:
  requests:
    cpu: "50m"
    memory: "256Mi"

  limits:
    cpu: "500m"
    memory: "512Mi"
```

### Resource Optimization Performed

Initial CPU requests were configured at 200m.

Observation:

* Pod scheduling issues occurred during rolling updates.
* Cluster CPU reservations were significantly higher than actual usage.

Optimization:

* CPU request reduced from 200m to 50m.
* Memory request maintained at 256Mi.
* CPU limit maintained at 500m.
* Memory limit maintained at 512Mi.

Benefits:

* Improved pod scheduling.
* Better node utilization.
* Reduced resource reservation.
* Lower infrastructure cost.

### Additional Cost Optimization Opportunities

1. Use HPA to scale only when required.
2. Right-size requests and limits based on observed metrics.
3. Use minimal node count during development and testing.
4. Delete the GKE cluster after assignment completion to avoid unnecessary cloud charges.

---

## Project Structure

```text
KubernetesDemo
│
├── src/
├── Dockerfile
├── pom.xml
├── README.md
│
└── kubernetes/
    ├── namespace.yaml
    ├── mysql-secret.yaml
    ├── mysql-pvc.yaml
    ├── mysql-service.yaml
    ├── mysql-deployment.yaml
    ├── student-configmap.yaml
    ├── student-api-deployment.yaml
    ├── student-api-service.yaml
    ├── student-api-ingress.yaml
    └── student-api-hpa.yaml
```

---

## Video Demonstration Checklist

The accompanying video demonstrates:

* All Kubernetes resources deployed and running.
* API call retrieving student records from MySQL.
* API pod deletion and automatic recovery.
* MySQL pod deletion and automatic recovery.
* Data persistence after MySQL pod recreation.
* Rolling update deployment strategy.
* Horizontal Pod Autoscaler scaling.
* Resource requests and limits.
* FinOps optimization considerations.

---

## Technologies Used

* Java 21
* Spring Boot
* Spring Data JPA
* MySQL 8.0
* Docker
* Kubernetes
* Google Kubernetes Engine (GKE)
* Docker Hub
* Kubernetes Ingress
* Horizontal Pod Autoscaler (HPA)
* ConfigMaps
* Secrets
* Persistent Volumes
