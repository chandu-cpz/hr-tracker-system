import grpc from "@grpc/grpc-js";
import protoloader from "@grpc/proto-loader";
import { GRPC_HOST, RECOMMENDATIONPROTOFILE_PATH, ATSPROTOFILE_PATH, PROTO_FILE_LOAD_OPTIONS } from "../constants.js";

let recommendationClient = null;
let atsClient = null;

export async function getRecommendationClient() {
    if (!recommendationClient) {
        const packageDefinitionRecommendation = protoloader.loadSync(
            RECOMMENDATIONPROTOFILE_PATH,
            PROTO_FILE_LOAD_OPTIONS
        );
        const RecommendationPackage = grpc.loadPackageDefinition(packageDefinitionRecommendation).recommendation;

        recommendationClient = new RecommendationPackage.RecommendationService(
            GRPC_HOST,
            grpc.credentials.createInsecure()
        );
    }
    return recommendationClient;
}

export async function getATSClient() {
    if (!atsClient) {
        const packageDefinitionATS = protoloader.loadSync(
            ATSPROTOFILE_PATH,
            PROTO_FILE_LOAD_OPTIONS
        );
        const ATSPackage = grpc.loadPackageDefinition(packageDefinitionATS).ats;

        atsClient = new ATSPackage.ATSService(
            GRPC_HOST,
            grpc.credentials.createInsecure()
        );
    }
    return atsClient;
}