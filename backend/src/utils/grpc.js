import grpc from "@grpc/grpc-js";
import protoloader from "@grpc/proto-loader";
import { GRPC_HOST, PROTOFILE_PATH, PROTO_FILE_LOAD_OPTIONS } from "../constants.js";

export async function getRecommendationClient() {
    const packageDefinitions = protoloader.loadSync(PROTOFILE_PATH, PROTO_FILE_LOAD_OPTIONS);
    const packages = grpc.loadPackageDefinition(packageDefinitions);
    const recommendationClient = new packages.recommendation.RecommendationService(
        GRPC_HOST,
        grpc.credentials.createInsecure()
    );
    return recommendationClient;
}
