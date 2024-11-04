import http from "k6/http";
import { check } from "k6";
export const options = {
    discardResponseBodies: true,
    scenarios: {
        contacts: {
            executor: "per-vu-iterations",
            vus: 10,
            iterations: 20,
            maxDuration: "30s",
        },
    },
};
export default function () {
    let res = http.get("http://localhost:8080/api/v1/products/popular");
    check(res, {
        "is status 200": (r) => r.status === 200,
    });
}